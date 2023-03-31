package edu.zut.hys.commentapi;

import edu.zut.hys.domain.Notice;
import edu.zut.hys.feignapi.clients.ArticleClient;
import edu.zut.hys.feignapi.clients.CommentClient;
import edu.zut.hys.feignapi.clients.NoticeClient;
import edu.zut.hys.feignapi.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.commentapi.generator.mapper")
@EnableFeignClients(clients = {UserClient.class, NoticeClient.class,
        CommentClient.class, ArticleClient.class})
public class CommentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentApiApplication.class, args);
    }

}
