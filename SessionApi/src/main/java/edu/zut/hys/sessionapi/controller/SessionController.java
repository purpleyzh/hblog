package edu.zut.hys.sessionapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.j2objc.annotations.AutoreleasePool;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.*;
import edu.zut.hys.feignapi.clients.MessageClient;
import edu.zut.hys.feignapi.clients.UserClient;
import edu.zut.hys.sessionapi.generator.mapper.SessionMapper;
import edu.zut.hys.sessionapi.generator.mapper.UserSessionMapper;
import edu.zut.hys.sessionapi.pojo.FullSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Author Hys
 * Date 2022/2/4 23:15
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionMapper sessionMapper;

    @Autowired
    UserSessionMapper userSessionMapper;

    @Autowired
    MessageClient messageClient;

    @Autowired
    UserClient userClient;

    @RequestMapping("/getsessionsbyuserid")
    public ResponseData getSessionsByUserid(@RequestParam("userid")Long userid){
        ResponseData responseData = new ResponseData();
        // 获取此人相关的所有session关系
        QueryWrapper<UserSession> userSessionQueryWrapper = new QueryWrapper<>();
        userSessionQueryWrapper.eq("userid", userid);
        List<UserSession> allUserSessions = userSessionMapper.selectList(userSessionQueryWrapper);
        // 如果没有session
        if(allUserSessions.size() == 0) {
            List<FullSession> fullSessions = new LinkedList<>();
            responseData.getData().put("sessions",fullSessions);
            HashMap<Long, User> usersMap = new HashMap<>();
            responseData.getData().put("users", usersMap);
            responseData.setCode(Constant.SUCCESS);
            return responseData;
        }
        // 获取此人相关的所有session
        List<Long> sessionids = new LinkedList<>();
        allUserSessions.forEach(userSession -> sessionids.add(userSession.getSessionid()));
        List<Session> sessions = sessionMapper.selectBatchIds(sessionids);

        HashMap<Long,List<Message>> messages = messageClient.getMessagesBySessionId(sessionids);
        List<FullSession> fullSessions = new LinkedList<>();
        sessions.forEach(session -> {
            List<UserSession> userSessions;
            userSessionQueryWrapper.clear();
            userSessionQueryWrapper.eq("sessionid", session.getSessionid());
            userSessions = userSessionMapper.selectList(userSessionQueryWrapper);
            fullSessions.add(new FullSession(session, messages.get(session.getSessionid()), userSessions));
        });

        //用户信息，会话相关的用户信息
        HashSet<Long> userIdsSet = new HashSet<>();
        for (FullSession fullSession : fullSessions) {
            fullSession.getUserSessions().forEach(userSession -> userIdsSet.add(userSession.getUserid()));
        }
        //添加用户信息
        List<Long> userIds = new LinkedList<>(userIdsSet);
        HashMap<Long, User> usersMap = userClient.getUsersByIds(userIds);

        //以最后一条消息排序
        Collections.sort(fullSessions,(o1,o2)->{
            int sizeo1 = o1.getMessages().size();
            int sizeo2 = o2.getMessages().size();
            return o2.getMessages().get(sizeo2-1).getCreatetime().compareTo(
                    o1.getMessages().get(sizeo1-1).getCreatetime()
            );
        });
        responseData.getData().put("sessions",fullSessions);
        responseData.getData().put("users", usersMap);
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    //init中分布式事务先创建session远程调用添加初始化消息
    @RequestMapping("/insertsession")
    public ResponseData insertSession(@RequestParam("sessiontype")String sessiontype,
                                      @RequestParam("userid")Long userid,
                                      @RequestParam(value = "touserid",required = false)Long touserid,
                                      @RequestParam(value = "sessionname",required = false)String sessionname){
        ResponseData responseData = new ResponseData();

        Session targetSession = null;
        List<Session> sessions;
        QueryWrapper<Session> sessionQueryWrapper = new QueryWrapper<>();
        QueryWrapper<UserSession> userSessionQueryWrapper = new QueryWrapper<>();
        // todo 找出此人所有相关private session然后找有没有存在对方的usersession
        if("private".equals(sessiontype)){
            //如果是私聊先判断之前有没有
            //有则继续 无则新建
            //如果有则二者中有一个是私聊创建人
            sessionQueryWrapper.eq("sessiontype",sessiontype)
                    .eq("userid", userid);
            sessions = sessionMapper.selectList(sessionQueryWrapper);
            //寻找对应的session对方里有没有touserid
            List<Long> sessionids = new LinkedList<>();
            List<UserSession> userSessions;

            if(!sessions.isEmpty()){
                sessions.forEach(session -> sessionids.add(session.getSessionid()));
                userSessionQueryWrapper.in("sessionid",sessionids);
                //根据sessionid找到userSession
                userSessions = userSessionMapper.selectList(userSessionQueryWrapper);
                for (UserSession userSession : userSessions) {
                    //找到该私聊
                    if (userSession.getUserid().equals(touserid)){
                        for (Session session : sessions) {
                            if (session.getSessionid().equals(userSession.getSessionid())){
                                targetSession = session;
                                break;
                            }
                        }
                        if(targetSession != null) {
                            break;
                        }
                    }
                }
            }

            //反向查找
            if(targetSession == null){
                sessionQueryWrapper.clear();
                sessionQueryWrapper.eq("sessiontype",sessiontype)
                        .eq("userid", touserid);
                sessions = sessionMapper.selectList(sessionQueryWrapper);
                //寻找对应的session对方里有没有touserid
                if(!sessions.isEmpty()){
                    sessionids.clear();
                    userSessionQueryWrapper.clear();
                    sessions.forEach(session -> sessionids.add(session.getSessionid()));
                    userSessionQueryWrapper.in("sessionid",sessionids);
                    userSessions = userSessionMapper.selectList(userSessionQueryWrapper);
                    for (UserSession userSession : userSessions) {
                        //找到该私聊
                        if (userSession.getUserid().equals(userid)){
                            for (Session session : sessions) {
                                if (session.getSessionid().equals(userSession.getSessionid())){
                                    targetSession = session;
                                    break;
                                }
                            }
                            if(targetSession != null) {
                                break;
                            }
                        }
                    }
                }
            }

            //确实没有则新建私聊
            if(targetSession == null){
                targetSession = new Session(userid, "private", "private");
                sessionMapper.insert(targetSession);
                UserSession u1 = new UserSession(userid, targetSession.getSessionid());
                UserSession u2 = new UserSession(touserid, targetSession.getSessionid());
                userSessionMapper.insert(u1);
                userSessionMapper.insert(u2);
                messageClient.initSession(targetSession.getSessionid(), userid);
            }
            responseData.getData().put("session", targetSession);
            responseData.setCode(Constant.SUCCESS);
            return responseData;
        }

        //对于群聊新建
        targetSession = new Session(userid, "group", sessionname);
        sessionMapper.insert(targetSession);
        UserSession userSession = new UserSession(userid, targetSession.getSessionid());
        userSessionMapper.insert(userSession);
        messageClient.initSession(targetSession.getSessionid(), userid);

        responseData.getData().put("session", targetSession);
        responseData.setCode(Constant.SUCCESS);

        return responseData;
    }

    // 根据名称搜索群聊
    @RequestMapping("/searchsessionsbysessionname")
    public ResponseData searchSessionsBySessionName(@RequestParam("keyword")String keyword){
        ResponseData responseData = new ResponseData();

        QueryWrapper<Session> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("sessionname", keyword);
        queryWrapper.eq("sessiontype","group");
        List<Session> sessions = sessionMapper.selectList(queryWrapper);
        responseData.getData().put("sessions", sessions);
        responseData.setCode(20000);
        return responseData;
    }

    // 加入群聊
    @RequestMapping("/joinsession")
    public ResponseData joinSession(@RequestParam("userid")Long userid,
                                    @RequestParam("sessionid")Long sessionid){
        ResponseData responseData = new ResponseData();
        QueryWrapper<UserSession> userSessionQueryWrapper = new QueryWrapper<>();
        userSessionQueryWrapper.eq("userid", userid).eq("sessionid", sessionid);
        UserSession userSession = userSessionMapper.selectOne(userSessionQueryWrapper);

        if(userSession == null){
            userSession = new UserSession(userid, sessionid);
            userSessionMapper.insert(userSession);
        }

        responseData.getData().put("userSession", userSession);
        responseData.setCode(20000);
        return responseData;
    }


}
