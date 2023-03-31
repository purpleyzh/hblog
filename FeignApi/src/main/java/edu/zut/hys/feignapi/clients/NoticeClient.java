package edu.zut.hys.feignapi.clients;

import edu.zut.hys.feignapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author Hys
 * Date 2022/2/21 17:01
 * Project AwakeningEra2
 */
@FeignClient(value = "noticeapi",configuration = FeignClientConfiguration.class)
public interface NoticeClient {

    @RequestMapping("/notice/feign/sendnotice")
    public void SendNotice(@RequestParam(value = "userid", required = false)Long userid, @RequestParam("sendtype")String sendtype,
                            @RequestParam("touserid")Long touserid, @RequestParam("bodytype")String bodytype,
                            @RequestParam("body")String body);

}
