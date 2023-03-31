package edu.zut.hys.followapi;

import edu.zut.hys.domain.Notice;
import edu.zut.hys.feignapi.clients.FileClient;
import edu.zut.hys.feignapi.clients.NoticeClient;
import edu.zut.hys.feignapi.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.followapi.generator.mapper")
@EnableFeignClients(clients = {UserClient.class, NoticeClient.class})
public class FollowApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FollowApiApplication.class, args);
    }

}
