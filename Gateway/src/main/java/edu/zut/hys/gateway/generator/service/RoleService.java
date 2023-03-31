package edu.zut.hys.gateway.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zut.hys.domain.Role;

import java.util.List;

/**
 *
 */
public interface RoleService extends IService<Role> {

    public List<Role> getRolesByIds(List<Long> ids);

}
