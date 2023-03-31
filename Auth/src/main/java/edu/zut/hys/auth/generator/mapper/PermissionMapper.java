package edu.zut.hys.auth.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zut.hys.auth.config.MybatisRedisCache;
import edu.zut.hys.domain.Permission;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @Entity edu.zut.hys.auth.generator.domain.Permission
 */
public interface PermissionMapper extends BaseMapper<Permission> {

}




