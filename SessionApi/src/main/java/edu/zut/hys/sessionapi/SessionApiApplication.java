package edu.zut.hys.sessionapi;

import edu.zut.hys.feignapi.clients.MessageClient;
import edu.zut.hys.feignapi.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.sessionapi.generator.mapper")
@EnableFeignClients(clients = {MessageClient.class, UserClient.class})
public class SessionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApiApplication.class, args);
    }

}
