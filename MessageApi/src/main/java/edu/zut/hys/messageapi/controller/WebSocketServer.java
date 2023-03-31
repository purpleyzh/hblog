package edu.zut.hys.messageapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;


/**
 * WebSocketServer
 * @author zhengkai.blog.csdn.net
 */
@ServerEndpoint("/message/imserver/{userId}")
@Component
public class WebSocketServer {

    static Log log= LogFactory.getLog(WebSocketServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";
    private Channel channel;
    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        WebSocketServer.rabbitTemplate = rabbitTemplate;
    }

    private final String exchange = "message";
    //创建监听队列，建立信道连接
    public void createQueue(Long userId){
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        channel = connection.createChannel(true);
        try{
            /**
             * 与生产者使用同一个交换机
             */
//            channel.exchangeDeclare(exchange, "direct",true);
            /**
             * 设置队列名称，持久队列
             */
            String queueName = "appuser"+userId;
            channel.queueDeclare(queueName,true,false,false,null);

            /*
              关联 exchange 和 queue
             */
            channel.queueBind(queueName, exchange, queueName);

            /*
              对信息做操作
             */
            //解析发送的报文
            //                    JSONObject jsonObject = JSON.parseObject(message);
            //来消息就推送
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);

                    if (StringUtils.isEmpty(message)) {
                        return;
                    }

                    /**
                     * 对信息做操作
                     */
                    log.info(message);
                    //解析发送的报文
//                    JSONObject jsonObject = JSON.parseObject(message);
                    if (webSocketMap.containsKey(String.valueOf(userId))) {
                        log.info(new Date(System.currentTimeMillis()).toLocalDate() + "推送消息给" + userId);
                        webSocketMap.get(String.valueOf(userId)).sendMessage(message);
                    } else {
                        log.error("请求的userId:" + userId + "不在该服务器上");
                    }
                }

            };
            //true 自动回复ack
            channel.basicConsume(queueName, true, consumer);
        }catch (Exception ex){
            log.error(userId+"队列监听断开");
        }
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;

        createQueue(Long.valueOf(userId));
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        try {
            channel.close();
            log.info("websocket断开channel关闭");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message) {
        log.info("用户消息:"+userId+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId",this.userId);
                String toUserId=jsonObject.getString("toUserId");

                //保存消息

                log.info(message);
                //解析发送的报文
//                    JSONObject jsonObject = JSON.parseObject(message);
                if(webSocketMap.containsKey(String.valueOf(userId))){
                    webSocketMap.get(String.valueOf(userId)).sendMessage(message);
                }else{
                    log.error("请求的userId:"+userId+"不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}