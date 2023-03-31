package edu.zut.hys.sessionapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.domain.User;
import edu.zut.hys.domain.UserSession;
import edu.zut.hys.sessionapi.generator.mapper.UserSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 23:15
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/session/feign")
public class SessionFeignController {

    @Autowired
    UserSessionMapper userSessionMapper;

    @RequestMapping("/getOtherUserIdBySessionId")
    public List<Long> getOtherUserIdBySessionId(@RequestParam("sessionid")Long sessionid){
        QueryWrapper<UserSession> userSessionQueryWrapper = new QueryWrapper<>();
        userSessionQueryWrapper.eq("sessionid", sessionid);
        List<UserSession> userSessions = userSessionMapper.selectList(userSessionQueryWrapper);
        List<Long> userids = new LinkedList<>();
        userSessions.forEach(userSession -> userids.add(userSession.getUserid()));
        return userids;
    }
}
