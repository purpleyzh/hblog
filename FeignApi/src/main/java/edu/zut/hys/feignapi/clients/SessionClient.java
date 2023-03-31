package edu.zut.hys.feignapi.clients;

import edu.zut.hys.feignapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author Hys
 * Date 2022/2/19 16:57
 * Project AwakeningEra2
 */
@FeignClient(value = "sessionapi",configuration = FeignClientConfiguration.class)
public interface SessionClient {

    @RequestMapping("/session/feign/getOtherUserIdBySessionId")
    public List<Long> getOtherUserIdBySessionId(@RequestParam("sessionid")Long sessionid);

}
