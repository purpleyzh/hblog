package edu.zut.hys.userapi;

import edu.zut.hys.feignapi.clients.FileClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@MapperScan("edu.zut.hys.userapi.generator.mapper")
@EnableFeignClients(clients = {FileClient.class})
@EnableDiscoveryClient
@SpringBootApplication
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}
