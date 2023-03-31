package edu.zut.hys.messageapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.domain.Message;
import edu.zut.hys.messageapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.messageapi.generator.domain.Message
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface MessageMapper extends BaseMapper<Message> {

}




