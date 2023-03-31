package edu.zut.hys.noticeapi.controller;

import com.alibaba.fastjson.JSON;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Notice;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.noticeapi.generator.mapper.NoticeMapper;
import edu.zut.hys.noticeapi.pojo.MQmessage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Hys
 * Date 2022/2/21 14:20
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/notice/feign")
public class NoticeFeignController {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    NoticeMapper noticeMapper;

    @RequestMapping("/sendnotice")
    public void SendNotice(@RequestParam(value = "userid", required = false)Long userid, @RequestParam("sendtype")String sendtype,
                           @RequestParam("touserid")Long touserid, @RequestParam("bodytype")String bodytype,
                           @RequestParam("body")String body){
        ResponseData responseData = new ResponseData();

        Notice notice = new Notice(userid, sendtype, touserid, bodytype, body);
        noticeMapper.insert(notice);

        MQmessage mQmessage = new MQmessage();
        mQmessage.setType("notice");
        mQmessage.getBody().put("notice",notice);
        String sendToMQ = JSON.toJSONString(mQmessage);

        log.info("推送消息到appuser"+touserid);
        rabbitTemplate.convertAndSend("message", "appuser"+touserid, sendToMQ);

        responseData.setCode(Constant.SUCCESS);
    }

}
