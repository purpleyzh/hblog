package edu.zut.hys.auth.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.auth.config.MybatisRedisCache;
import edu.zut.hys.domain.User;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.auth.generator.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

}




