package edu.zut.hys.gateway.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.User;
import edu.zut.hys.gateway.generator.service.UserService;
import edu.zut.hys.gateway.generator.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

}




