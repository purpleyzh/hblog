package edu.zut.hys.followapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.domain.Follow;
import edu.zut.hys.followapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.followapi.generator.domain.Follow
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface FollowMapper extends BaseMapper<Follow> {

}




