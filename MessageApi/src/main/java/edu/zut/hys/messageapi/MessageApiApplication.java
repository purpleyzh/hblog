package edu.zut.hys.messageapi;

import edu.zut.hys.feignapi.clients.MessageClient;
import edu.zut.hys.feignapi.clients.SessionClient;
import edu.zut.hys.feignapi.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.messageapi.generator.mapper")
@EnableFeignClients(clients = {SessionClient.class, UserClient.class})
public class MessageApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageApiApplication.class, args);
    }

}
