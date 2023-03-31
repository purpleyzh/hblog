package edu.zut.hys.heartenapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.domain.Hearten;
import edu.zut.hys.heartenapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.heartenapi.generator.domain.Hearten
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface HeartenMapper extends BaseMapper<Hearten> {

}




