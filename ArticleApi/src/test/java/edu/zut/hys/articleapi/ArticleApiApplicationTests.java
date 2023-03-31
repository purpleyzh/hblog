package edu.zut.hys.articleapi;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zut.hys.articleapi.generator.service.ArticleService;
import edu.zut.hys.articleapi.pojo.EsArticle;
import edu.zut.hys.domain.Article;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class ArticleApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    ArticleService articleService;

    @Test
    void Test(){
        String s = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<p style=\"padding-left: 40px; text-align: left;\">世界文明的发源地有二：一是科学研究室。一是监狱。</p>" +
                "<p style=\"padding-left: 40px; text-align: left;\">我们青年立志出了研究室就入监狱，出了监狱就入研究室，这才是人生最高尚优美的生活。</p>" +
                "<p style=\"padding-left: 40px; text-align: left;\">从这两处发生的文明，才是真文明，才是有生命有价值的文明。</p>" +
                "</body>" +
                "</html>";
        String s2 = "<!DOCTYPE html><html><head></head><body><p style=\"padding-left: 40px; text-align: left;\">世界文明的发源地有二：一是科学研究室。一是监狱。</p><p style=\"padding-left: 40px; text-align: left;\">我们青年立志出了研究室就入监狱，出了监狱就入研究室，这才是人生最高尚优美的生活。</p><p style=\"padding-left: 40px; text-align: left;\">从这两处发生的文明，才是真文明，才是有生命有价值的文明。</p></body></html>";
        System.out.println(s);
    }

    RestHighLevelClient client;

    @BeforeEach
    void initClient(){
         this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://101.35.82.76:9200")
        ));
    }

//    @Test
//    void createIndex(){
//        CreateIndexRequest request = new CreateIndexRequest("testindex");
//        request.source("DSL", XContentType.JSON);
//        try {
//            client.indices().create(request, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void existindex(){
        GetIndexRequest request = new GetIndexRequest("article");
        try {
            System.out.println(client.indices().exists(request, RequestOptions.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void search() throws IOException {
        SearchRequest request = new SearchRequest("article");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println(total);
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @Test
    void BulkRequest() throws IOException {
        List<Article> articleList = articleService.list();
        List<EsArticle> esArticles = new LinkedList<>();
        articleList.forEach(item->{
            esArticles.add(new EsArticle(item));
        });

        BulkRequest request = new BulkRequest();
        for (EsArticle esArticle : esArticles) {
            System.out.println(JSON.toJSONString(esArticle));
            request.add(new IndexRequest("article").id(String.valueOf(esArticle.getArticleid()))
                    .source(JSON.toJSONString(esArticle), XContentType.JSON));
        }

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

}
