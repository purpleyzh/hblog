package edu.zut.hys.articleapi.controller;

import edu.zut.hys.articleapi.generator.mapper.ArticleMapper;
import edu.zut.hys.domain.Article;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/25 0:12
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/article/feign")
public class ArticleFeignController {

    @Autowired
    UserClient userClient;

    @Autowired
    ArticleMapper articleMapper;

    @RequestMapping("/getuserbyarticleid")
    User getUserByArticleId(@RequestParam("articleid")Long articleid){
        Article article = articleMapper.selectById(articleid);
        List<Long> userids = new LinkedList<>();
        userids.add(article.getUserid());
        HashMap<Long, User> userHashMap = userClient.getUsersByIds(userids);
        return userHashMap.get(article.getUserid());
    }

}
