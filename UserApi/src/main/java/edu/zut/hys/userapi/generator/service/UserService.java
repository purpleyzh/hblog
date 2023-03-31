package edu.zut.hys.userapi.generator.service;

import edu.zut.hys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User> {

    public void clearCache();

}
