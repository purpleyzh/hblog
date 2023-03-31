package edu.zut.hys.feignapi.clients;

import edu.zut.hys.domain.Message;
import edu.zut.hys.feignapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/19 1:52
 * Project AwakeningEra2
 */
@FeignClient(value = "messageapi",configuration = FeignClientConfiguration.class)
public interface MessageClient {

    @RequestMapping("/message/feign/getmessagesbysessionid")
    public HashMap<Long,List<Message>> getMessagesBySessionId(@RequestParam("ids") List<Long> ids);

    @RequestMapping("/message/feign/initsession")
    public void initSession(@RequestParam("sessionid")Long sessionid,
                            @RequestParam("userid")Long userid);

}
