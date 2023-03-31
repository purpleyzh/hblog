package edu.zut.hys.auth.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.auth.config.MybatisRedisCache;
import edu.zut.hys.domain.Role;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.auth.generator.domain.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

}




