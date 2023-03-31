package edu.zut.hys.articleapi.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.articleapi.generator.service.ArticleService;
import edu.zut.hys.articleapi.generator.mapper.ArticleMapper;
import edu.zut.hys.domain.Article;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
implements ArticleService{

}




