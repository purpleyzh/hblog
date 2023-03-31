package edu.zut.hys.noticeapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.noticeapi.generator.mapper")
public class NoticeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeApiApplication.class, args);
    }

}
