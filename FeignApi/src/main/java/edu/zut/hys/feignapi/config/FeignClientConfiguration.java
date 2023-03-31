package edu.zut.hys.feignapi.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author Hys
 * Date 2022/1/28 23:23
 * Project AwakeningEra2
 */
public class FeignClientConfiguration {

    @Bean
    public Logger.Level logerLevel(){
        return Logger.Level.BASIC;
    }

}
