package edu.zut.hys.auth.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zut.hys.domain.User;

/**
 *
 */
public interface UserService extends IService<User> {

    public User getOneByUsername(String username);

}
