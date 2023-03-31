package edu.zut.hys.articleapi.mq;

import com.alibaba.fastjson.JSON;
import edu.zut.hys.articleapi.constant.RabbitMQConstant;
import edu.zut.hys.articleapi.pojo.EsArticle;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author Hys
 * Date 2022/2/27 16:59
 * Project AwakeningEra2
 */
@Component
public class ArticleListener {

    @Autowired
    RestHighLevelClient client;

    @RabbitListener(queues = RabbitMQConstant.ARTICLE_INSERT_QUEUE)
    public void listenerInsertArticle(String json){
        try {
            EsArticle esArticle = JSON.parseObject(json, EsArticle.class);
            IndexRequest request = new IndexRequest("article");
            request.id(esArticle.getArticleid().toString());
            request.source(json, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConstant.ARTICLE_DELETE_QUEUE)
    public void listenerDeleteArticle(String json){
        //逻辑删除status = 2
        EsArticle esArticle = JSON.parseObject(json, EsArticle.class);
        try {
            IndexRequest request = new IndexRequest("article");
            request.id(esArticle.getArticleid().toString());
            request.source(json, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //物理删除
//        DeleteRequest request = new DeleteRequest("article").id(esArticle.getArticleid().toString());
//        try {
//            client.delete(request, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
