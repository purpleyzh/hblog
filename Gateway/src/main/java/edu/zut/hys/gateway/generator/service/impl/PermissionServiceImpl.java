package edu.zut.hys.gateway.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.j2objc.annotations.AutoreleasePool;
import edu.zut.hys.domain.Permission;
import edu.zut.hys.gateway.generator.service.PermissionService;
import edu.zut.hys.gateway.generator.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 */
@Slf4j
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
implements PermissionService{

    @Autowired
    PermissionMapper permissionMapper;

    @Cacheable(value = "permission", keyGenerator = "keyGenerator")
    @Override
    public Permission getOneByResource(String resource) {
        log.info("此时应该有缓存");
        return permissionMapper.selectOne(new QueryWrapper<Permission>().eq("resource",resource));
    }
}




