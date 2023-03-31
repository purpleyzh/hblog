package edu.zut.hys.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.auth.constant.MessageConstant;
import edu.zut.hys.auth.domain.SecurityUser;
import edu.zut.hys.auth.generator.service.impl.RoleServiceImpl;
import edu.zut.hys.auth.generator.service.impl.UserRoleServiceImpl;
import edu.zut.hys.auth.generator.service.impl.UserServiceImpl;
import edu.zut.hys.domain.Role;
import edu.zut.hys.domain.User;
import edu.zut.hys.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理业务类
 * Created by hys on 2022/1/30.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        user = userService.getOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }

        List<UserRole> userRoles = userRoleService.listByUserid(user.getUserid());

        List<Long> roleids = new ArrayList<>();
        userRoles.forEach((item)->{
            roleids.add(item.getRoleid());
        });
        SecurityUser securityUser = new SecurityUser(user, (ArrayList<Role>) roleService.listByRoleIds(roleids));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

}
