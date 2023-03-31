package edu.zut.hys.gateway.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.RolePermission;
import edu.zut.hys.gateway.generator.service.RolePermissionService;
import edu.zut.hys.gateway.generator.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
implements RolePermissionService{

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Cacheable(value = "rolepermission", keyGenerator = "keyGenerator")
    @Override
    public List<RolePermission> getRolePermissionsByPermissionid(Long permissionid){
        return rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("permissionid",permissionid));
    }

}




