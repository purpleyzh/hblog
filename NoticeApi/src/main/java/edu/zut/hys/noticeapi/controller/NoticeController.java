package edu.zut.hys.noticeapi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Message;
import edu.zut.hys.domain.Notice;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.noticeapi.generator.mapper.NoticeMapper;
import edu.zut.hys.noticeapi.pojo.MQmessage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.amqp.rabbit.connection.RabbitAccessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author Hys
 * Date 2022/2/21 14:20
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/getnoticesbyuserid")
    public ResponseData getNoticesByUserId(@RequestParam("userid")Long userid){
        ResponseData responseData = new ResponseData();

        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq("touserid", userid).orderByDesc("createtime");
        List<Notice> notices = noticeMapper.selectList(noticeQueryWrapper);
        responseData.getData().put("notices", notices);

        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    @RequestMapping("/sendmessage")
    public ResponseData SendMessage(@RequestParam("userid")Long userid, @RequestParam("sendtype")String sendtype,
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
        return responseData;
    }

}
