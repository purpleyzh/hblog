package edu.zut.hys.auth.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zut.hys.domain.RolePermission;

/**
 *
 */
public interface RolePermissionService extends IService<RolePermission> {

    public void clearCache();

}
