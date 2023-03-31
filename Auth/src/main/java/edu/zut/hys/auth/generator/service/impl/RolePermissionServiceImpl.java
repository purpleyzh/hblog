package edu.zut.hys.auth.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.auth.generator.service.RolePermissionService;
import edu.zut.hys.auth.generator.mapper.RolePermissionMapper;
import edu.zut.hys.domain.RolePermission;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
implements RolePermissionService{

    @CacheEvict(value = {"role","permission","rolepermission"},allEntries = true)
    @Override
    public void clearCache() {

    }
}




