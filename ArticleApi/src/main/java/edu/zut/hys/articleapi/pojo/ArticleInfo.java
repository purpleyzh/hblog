package edu.zut.hys.articleapi.pojo;

import edu.zut.hys.domain.Appfile;
import edu.zut.hys.domain.Article;
import edu.zut.hys.domain.User;
import lombok.Data;

/**
 * Author Hys
 * Date 2022/2/4 19:06
 * Project AwakeningEra2
 */
@Data
public class ArticleInfo {
    Article article;
    User writer;
    Appfile headshot;

    public ArticleInfo(Article article, User user, Appfile appfile) {
        this.article = article;
        this.writer = user;
        this.headshot = appfile;
    }
}
