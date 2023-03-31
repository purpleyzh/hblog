package edu.zut.hys.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Author Hys
 * Date 2022/1/29 23:58
 * Project AwakeningEra2
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("edu.zut.hys.gateway.generator.mapper")
@EnableCaching
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
