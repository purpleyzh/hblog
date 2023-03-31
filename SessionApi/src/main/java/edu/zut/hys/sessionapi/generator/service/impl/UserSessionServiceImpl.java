package edu.zut.hys.sessionapi.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.UserSession;
import edu.zut.hys.sessionapi.generator.service.UserSessionService;
import edu.zut.hys.sessionapi.generator.mapper.UserSessionMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserSessionServiceImpl extends ServiceImpl<UserSessionMapper, UserSession>
implements UserSessionService{

}




