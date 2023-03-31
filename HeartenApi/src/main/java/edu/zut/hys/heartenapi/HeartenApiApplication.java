package edu.zut.hys.heartenapi;

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
import org.springframework.cloud.openfeign.FeignClient;

import javax.xml.stream.events.Comment;

/**
 * Author Hys
 * Date 2022/2/4 21:10
 * Project AwakeningEra2
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.heartenapi.generator.mapper")
@EnableFeignClients(clients = {UserClient.class, NoticeClient.class,
        CommentClient.class, ArticleClient.class})
public class HeartenApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeartenApiApplication.class);
    }
}
