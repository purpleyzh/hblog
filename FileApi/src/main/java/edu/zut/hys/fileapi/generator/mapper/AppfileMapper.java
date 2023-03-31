package edu.zut.hys.fileapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.domain.Appfile;
import edu.zut.hys.fileapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.fileapi.generator.domain.Appfile
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface AppfileMapper extends BaseMapper<Appfile> {

}




