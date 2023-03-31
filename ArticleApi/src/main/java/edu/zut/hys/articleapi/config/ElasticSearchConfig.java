package edu.zut.hys.articleapi.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author Hys
 * Date 2022/2/27 17:04
 * Project AwakeningEra2
 */
@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient initClient(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://101.35.82.76:9200")
        ));
    }

}
