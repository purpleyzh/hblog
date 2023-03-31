package edu.zut.hys.gateway.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zut.hys.domain.Permission;

/**
 *
 */
public interface PermissionService extends IService<Permission> {

    public Permission getOneByResource(String resource);

}
