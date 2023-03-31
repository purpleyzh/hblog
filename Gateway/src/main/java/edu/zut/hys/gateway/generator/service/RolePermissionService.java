package edu.zut.hys.gateway.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zut.hys.domain.RolePermission;

import java.util.List;

/**
 *
 */
public interface RolePermissionService extends IService<RolePermission> {

    public List<RolePermission> getRolePermissionsByPermissionid(Long permissionid);

}
