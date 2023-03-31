package edu.zut.hys.messageapi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Message;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.SessionClient;
import edu.zut.hys.feignapi.clients.UserClient;
import edu.zut.hys.messageapi.generator.mapper.MessageMapper;
import edu.zut.hys.messageapi.pojo.MQmessage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.logging.Logger;

/**
 * Author Hys
 * Date 2022/2/4 23:19
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    SessionClient sessionClient;

    @Autowired
    UserClient userClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/getmessagesbysessionid")
    public ResponseData getMessagesBySessionId(@RequestParam("sessionid")Long sessionid){
        ResponseData responseData = new ResponseData();

        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("sessionid", sessionid);
        List<Message> messages = messageMapper.selectList(messageQueryWrapper);

        responseData.getData().put("messages", messages);
        return responseData;
    }

    @RequestMapping("/sendmessage")
    public ResponseData SendMessage(@RequestParam("sessionid")Long sessionid, @RequestParam("messagetype")String messagetype,
                                    @RequestParam("userid")Long userid, @RequestParam("messagebody")String messagebody){
        ResponseData responseData = new ResponseData();
        //考虑分库应该使用分布式id，如果分库分表不同的消息自增id会有重复
        Message message = new Message(userid, messagetype, sessionid, messagebody);
        messageMapper.insert(message);
        //补充用户信息
        List<Long> selectuserids = new LinkedList<>();
        selectuserids.add(userid);
        HashMap<Long, User> usermap = userClient.getUsersByIds(selectuserids);

        List<Long> otherUserId = sessionClient.getOtherUserIdBySessionId(sessionid);
        MQmessage mQmessage = new MQmessage();
        mQmessage.setType("message");
        mQmessage.getBody().put("message",message);
        mQmessage.getBody().put("user",usermap.get(userid));
        String sendToMQ = JSON.toJSONString(mQmessage);

        otherUserId.forEach(touserid->{
            log.info("路由消息到appuser"+touserid+"的队列");
            rabbitTemplate.convertAndSend("message", "appuser"+touserid, sendToMQ);
        });

        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

}
