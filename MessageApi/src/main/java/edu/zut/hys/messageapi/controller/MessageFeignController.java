package edu.zut.hys.messageapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.domain.Message;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.messageapi.generator.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 23:20
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/message/feign")
public class MessageFeignController {

    @Autowired
    MessageMapper messageMapper;

    @RequestMapping("/getmessagesbysessionid")
    public HashMap<Long,List<Message>> getMessagesBySessionId(@RequestParam("ids") List<Long> ids){
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        HashMap<Long,List<Message>> sessions = new HashMap<>();
        ids.forEach(id->{
            messageQueryWrapper.eq("sessionid", id);
            List<Message> asession = messageMapper.selectList(messageQueryWrapper);
            sessions.put(id,asession);
            messageQueryWrapper.clear();
        });
        return sessions;
    }

    @RequestMapping("/initsession")
    public void initSession(@RequestParam("sessionid")Long sessionid,
                            @RequestParam("userid")Long userid){
        Message message = new Message(userid, "text", sessionid, "你好");
        messageMapper.insert(message);
    }

}
