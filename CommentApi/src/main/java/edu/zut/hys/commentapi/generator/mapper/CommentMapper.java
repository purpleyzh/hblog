package edu.zut.hys.commentapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.commentapi.config.MybatisRedisCache;
import edu.zut.hys.domain.Comment;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.commentapi.generator.domain.Comment
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface CommentMapper extends BaseMapper<Comment> {

}




