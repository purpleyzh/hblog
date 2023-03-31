package edu.zut.hys.auth.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.auth.generator.service.UserService;
import edu.zut.hys.auth.generator.mapper.UserMapper;
import edu.zut.hys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

    @Autowired
    UserMapper userMapper;

    @Cacheable(value = "user",keyGenerator = "keyGenerator")
    @Override
    public User getOneByUsername(String username){
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

}




