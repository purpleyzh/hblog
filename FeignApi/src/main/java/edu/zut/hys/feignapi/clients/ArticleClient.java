package edu.zut.hys.feignapi.clients;

import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author Hys
 * Date 2022/2/25 0:09
 * Project AwakeningEra2
 */
@FeignClient(value = "articleapi",configuration = FeignClientConfiguration.class)
public interface ArticleClient {

    @RequestMapping("/article/feign/getuserbyarticleid")
    User getUserByArticleId(@RequestParam("articleid")Long articleid);

}
