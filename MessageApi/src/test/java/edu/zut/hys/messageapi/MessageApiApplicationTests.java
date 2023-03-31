package edu.zut.hys.messageapi;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
class MessageApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String exchange = "message";

//    @Test
//    public void createQueue(){
//        String userId = "1";
//        Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(true);
//
//        try{
//            /**
//             * 与生产者使用同一个交换机
//             */
//            channel.exchangeDeclare(exchange, "direct",true);
//            /**
//             * 获取一个随机的队列名称，使用默认方式，产生的队列为临时队列，在没有消费者时将会自动删除
//             */
//            String queueName = "appuser"+userId;
//            channel.queueDeclare(queueName,true,false,false,null);
//
//            /**
//             * 关联 exchange 和 queue ，因为是广播无需指定routekey，routingKey设置为空字符串
//             */
////             channel.queueBind(queue, exchange, routingKey)
//            channel.queueBind(queueName, exchange, queueName);
//
//            Consumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope,
//                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    String message = new String(body, "UTF-8");
//
//                    if(StringUtils.isEmpty(message)){
//                        return;
//                    }
//
//                    /**
//                     * 对信息做操作
//                     */
//                }
//
//            };
//            //true 自动回复ack
//            channel.basicConsume(queueName, true, consumer);
//        }catch (Exception ex){
//        }
//    }

//    class Node{
//        int x;
//        int y;
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof Node)) return false;
//
//            Node node = (Node) o;
//
//            if (x != node.x) return false;
//            return y == node.y;
//        }
//
//        @Override
//        public int hashCode() {
//            int result = x;
//            result = 31 * result + y;
//            return result;
//        }
//    }
}
