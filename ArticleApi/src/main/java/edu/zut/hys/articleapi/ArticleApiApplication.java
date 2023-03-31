package edu.zut.hys.articleapi;

import edu.zut.hys.feignapi.clients.FileClient;
import edu.zut.hys.feignapi.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.articleapi.generator.mapper")
@EnableFeignClients(clients = {FileClient.class, UserClient.class})
public class ArticleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApiApplication.class, args);
    }

}
