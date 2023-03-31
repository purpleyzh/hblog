package edu.zut.hys.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Author Hys
 * Date 2022/1/30 0:02
 * Project AwakeningEra2
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.auth.generator.mapper")
@EnableCaching
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }
}
