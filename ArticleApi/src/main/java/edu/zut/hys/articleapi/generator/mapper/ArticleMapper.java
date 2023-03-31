package edu.zut.hys.articleapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.articleapi.config.MybatisRedisCache;
import edu.zut.hys.domain.Article;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.articleapi.generator.domain.Article
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface ArticleMapper extends BaseMapper<Article> {

}




