package edu.zut.hys.sessionapi.generator.mapper;

import edu.zut.hys.domain.UserSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.sessionapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.sessionapi.generator.domain.UserSession
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface UserSessionMapper extends BaseMapper<UserSession> {

}




