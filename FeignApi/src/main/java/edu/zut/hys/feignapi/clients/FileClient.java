package edu.zut.hys.feignapi.clients;

import edu.zut.hys.domain.Appfile;
import edu.zut.hys.feignapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * Author Hys
 * Date 2022/1/28 23:13
 * Project AwakeningEra2
 */
@FeignClient(value = "fileapi",configuration = FeignClientConfiguration.class)
public interface FileClient {

    @RequestMapping("/file/getheadshot")
    String getHeadshot();

    @RequestMapping("/file/feign/getfilesbyids")
    HashMap<Long, Appfile> getFilesByIds(@RequestParam("ids")List<Long> ids);

}
