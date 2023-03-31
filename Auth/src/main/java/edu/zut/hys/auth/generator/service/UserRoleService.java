package edu.zut.hys.auth.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zut.hys.domain.UserRole;

import java.util.List;

/**
 *
 */
public interface UserRoleService extends IService<UserRole> {

    List<UserRole> listByUserid(Long userid);

}
