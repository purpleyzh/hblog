package edu.zut.hys.articleapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zut.hys.articleapi.constant.RabbitMQConstant;
import edu.zut.hys.articleapi.generator.mapper.ArticleMapper;
import edu.zut.hys.articleapi.generator.service.ArticleService;
import edu.zut.hys.articleapi.pojo.ArticleInfo;
import edu.zut.hys.articleapi.pojo.EsArticle;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Appfile;
import edu.zut.hys.domain.Article;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.FileClient;
import edu.zut.hys.feignapi.clients.UserClient;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.logging.log4j.LogBuilder;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.util.CollectionUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.eql.EqlSearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * Author Hys
 * Date 2022/2/4 17:52
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserClient userClient;

    @Autowired
    FileClient fileClient;

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Autowired
    RestHighLevelClient client;
    

    @RequestMapping("/getarticle")
    public ResponseData getArticlePage(@RequestParam("current")Integer current, @RequestParam("size")Integer size){
        ResponseData responseData = new ResponseData();
        responseData.setCode(Constant.SUCCESS);
        Page<Article> page = new Page<>(current, size);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("status", "1");
        IPage<Article> articleIPage = articleMapper.selectPage(page,articleQueryWrapper);

        //article
        List<Article> articles = articleIPage.getRecords();

        List<ArticleInfo> articleInfos = fillArticles(articles);

        responseData.getData().put("pageNum",articleIPage.getPages());
        responseData.getData().put("articles", articleInfos);
        return responseData;
    }


    @RequestMapping("/getarticleinelasticsearch")
    public ResponseData getArticleInElasticSearch(@RequestParam("current")Integer current, @RequestParam("size")Integer size,
                                                  @RequestParam(value = "keyword",required = false)String keyword){
        ResponseData responseData = new ResponseData();
        responseData.setCode(Constant.SUCCESS);

        //获取文章
        List<Article> articles = new LinkedList<>();
        //建立请求
        SearchRequest request = new SearchRequest("article");
        //填充DSL
        //填充bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //关键词匹配
        boolQueryBuilder.filter(QueryBuilders.termQuery("status","1"));
        if(keyword == null || "".equals(keyword)){
            //全部查询
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }else{
            log.info("查询词"+keyword);
            //分词匹配
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(keyword,"title","body"));
        }
        request.source().query(boolQueryBuilder);
        //填充highlight
        request.source().highlighter(new HighlightBuilder().field("body").requireFieldMatch(false)
                .field("name").requireFieldMatch(false)
                .preTags("<span style='color:blue'>").postTags("</span>")
                .fragmentSize(800000)
                .numOfFragments(0));
        //结果排序
        request.source().sort("createtime", SortOrder.DESC);
        //分页
        request.source().from((current-1)*size).size(size);
        SearchResponse response = null;
        try {
            //获取响应
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        //处理结果
        for (SearchHit hit : hits) {
            Article item = new Article(JSON.parseObject(hit.getSourceAsString(), edu.zut.hys.pojo.EsArticle.class));
            if(keyword != null && !"".equals(keyword)){
                //获取所有高亮结果
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if(!CollectionUtils.isEmpty(highlightFields)){
                    //对body中的高亮处理
                    HighlightField body = highlightFields.get("body");
                    if(body!=null){
                        StringBuffer bodyString = new StringBuffer();
                        //循环拼接
                        for (Text fragment : body.getFragments()) {
                            bodyString.append(fragment.string());
                        }
                        log.info("高亮\n"+bodyString);
                        //更改实体内容
                        item.setBody(bodyString.toString());
                    }
                    //对title中的高亮处理
                    HighlightField title = highlightFields.get("title");
                    if(title!=null){
                        StringBuffer titleString = new StringBuffer();
                        //循环拼接
                        for (Text fragment : title.getFragments()) {
                            titleString.append(fragment.string());
                        }
                        log.info("高亮\n"+titleString);
                        //更改实体内容
                        item.setTitle(titleString.toString());
                    }
                }
            }
            articles.add(item);
        }

        List<ArticleInfo> articleInfos = fillArticles(articles);

        responseData.getData().put("articles", articleInfos);
        return responseData;
    }
    
    @RequestMapping("/insertarticle")
    public ResponseData insertArticle(@RequestBody String data){
        JSONObject jsonObject = JSON.parseObject(data);
        Long userid = Long.valueOf(jsonObject.getString("userid"));
        String title = jsonObject.getString("title");
        String body = jsonObject.getString("body");
        ResponseData responseData = new ResponseData();

        Article article = new Article(userid, title, body);
        articleMapper.insert(article);

        //消息队列同步ElasticSearch
        EsArticle esArticle = new EsArticle(article);
        rabbitTemplate.convertAndSend(RabbitMQConstant.ARTICLE_EXCHANGE,
                RabbitMQConstant.ARTICLE_INSERT_KEY, JSON.toJSONString(esArticle));

        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    @RequestMapping("/deletearticle")
    public ResponseData deleteArticle(@RequestParam("userid")Long userid, @RequestParam("articleid")Long articleid){
        ResponseData responseData = new ResponseData();
        Article article = articleMapper.selectById(articleid);
        article.setStatus("2");
        articleMapper.updateById(article);

        EsArticle esArticle = new EsArticle(article);

        rabbitTemplate.convertAndSend(RabbitMQConstant.ARTICLE_EXCHANGE,
                RabbitMQConstant.ARTICLE_DELETE_KEY, JSON.toJSONString(esArticle));

        responseData.setCode(Constant.SUCCESS);
        responseData.getData().put("status", "2");
        return responseData;
    }

    List<ArticleInfo> fillArticles(List<Article> articles){
        List<ArticleInfo> articleInfos = new LinkedList<>();

        if(articles.isEmpty()){
            return articleInfos;
        }

        //user
        List<Long> userids = new LinkedList<>();
        articles.forEach(item->{
            userids.add(item.getUserid());
        });
        HashMap<Long, User> users = userClient.getUsersByIds(userids);

        //headshot
        List<Long> fileids = new LinkedList<>();
        users.forEach((key, value)->{
            fileids.add(value.getHeadshotid());
        });
        HashMap<Long, Appfile> appfiles = fileClient.getFilesByIds(fileids);

        Article article;
        User writer;
        Appfile headshot;
        for (int i = 0; i < articles.size(); i++) {
            article = articles.get(i);
            writer = users.get(article.getUserid());
            headshot = appfiles.get(writer.getHeadshotid());
            articleInfos.add(new ArticleInfo(article, writer, headshot));
        }
        return articleInfos;
    }

}
