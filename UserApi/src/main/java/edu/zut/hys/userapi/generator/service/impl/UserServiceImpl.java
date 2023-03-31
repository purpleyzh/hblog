package edu.zut.hys.userapi.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.User;
import edu.zut.hys.userapi.generator.service.UserService;
import edu.zut.hys.userapi.generator.mapper.UserMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

    @CacheEvict(value = {"role","user","userrole"},allEntries = true)
    @Override
    public void clearCache() {

    }

}




