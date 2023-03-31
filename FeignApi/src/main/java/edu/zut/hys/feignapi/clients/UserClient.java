package edu.zut.hys.feignapi.clients;

import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 19:09
 * Project AwakeningEra2
 */
@FeignClient(value = "userapi",configuration = FeignClientConfiguration.class)
public interface UserClient {

    @RequestMapping("/user/feign/getusersbyids")
    public HashMap<Long,User> getUsersByIds(@RequestParam("ids")List<Long> ids);

    @RequestMapping("/user/feign/updateheadshot")
    public void updateHeadshot(@RequestParam("userid")Long userid,
                               @RequestParam("fileid")Long fileid);
}
