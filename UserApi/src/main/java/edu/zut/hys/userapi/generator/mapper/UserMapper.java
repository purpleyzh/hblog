package edu.zut.hys.userapi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.domain.User;
import edu.zut.hys.userapi.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.userapi.generator.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

}




