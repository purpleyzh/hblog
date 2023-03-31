package edu.zut.hys.noticeapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.domain.Notice;
import edu.zut.hys.noticeapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.noticeapi.generator.domain.Notice
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface NoticeMapper extends BaseMapper<Notice> {

}




