package edu.zut.hys.articleapi.pojo;

import edu.zut.hys.domain.Article;
import lombok.Data;

import java.util.Date;

/**
 * Author Hys
 * Date 2022/2/27 16:18
 * Project AwakeningEra2
 */

@Data
public class EsArticle {

    private Long articleid;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private Date createtime;

    /**
     *
     */
    private Long userid;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String body;

    public EsArticle() {}

    public EsArticle(Article article) {
        this.articleid = article.getArticleid();
        this.status = article.getStatus();
        this.createtime = article.getCreatetime();
        this.userid = article.getUserid();
        this.title = article.getTitle();
        this.body = article.getBody();
    }
}
