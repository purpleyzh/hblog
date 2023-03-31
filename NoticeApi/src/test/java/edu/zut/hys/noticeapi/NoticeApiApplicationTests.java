package edu.zut.hys.noticeapi;

import com.alibaba.fastjson.JSON;
import edu.zut.hys.domain.Message;
import edu.zut.hys.noticeapi.pojo.MQmessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class NoticeApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void sendMessageToExchange(){
//        MQmessage mQmessage = new MQmessage();
//        mQmessage.setType("message");
//        Message message = new Message();
//        message.setMessageid(1l);
//        message.setMessagebody("111");
//        message.setMessagetype("group");
//        message.setCreatetime(new Date(System.currentTimeMillis()));
//        message.setSessionid(1l);
//        message.setStatus("1");
//        message.setUserid(2l);
//        mQmessage.getBody().put("message",message);
//        rabbitTemplate.convertAndSend("message", "appuser1", JSON.toJSONString(mQmessage));
    }

}
