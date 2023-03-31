package edu.zut.hys.articleapi.config;

import edu.zut.hys.articleapi.constant.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author Hys
 * Date 2022/2/27 16:44
 * Project AwakeningEra2
 */

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitMQConstant.ARTICLE_EXCHANGE, true, false);
    }

    @Bean
    public Queue insertQueue(){
        return new Queue(RabbitMQConstant.ARTICLE_INSERT_QUEUE, true);
    }

    @Bean
    public Queue deleteQueue(){
        return new Queue(RabbitMQConstant.ARTICLE_DELETE_QUEUE, true);
    }

    @Bean
    public Binding insertQueueBinding(){
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(RabbitMQConstant.ARTICLE_INSERT_KEY);
    }

    @Bean
    public Binding deleteQueueBinding(){
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(RabbitMQConstant.ARTICLE_DELETE_KEY);
    }

}
