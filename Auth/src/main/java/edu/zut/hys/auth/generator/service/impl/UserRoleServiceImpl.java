package edu.zut.hys.auth.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.j2objc.annotations.AutoreleasePool;
import edu.zut.hys.auth.generator.service.UserRoleService;
import edu.zut.hys.auth.generator.mapper.UserRoleMapper;
import edu.zut.hys.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
implements UserRoleService{

    @Autowired
    UserRoleMapper userRoleMapper;

    @Cacheable(value = "userrole",keyGenerator = "keyGenerator")
    @Override
    public List<UserRole> listByUserid(Long userid) {
        return userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("userid",userid));
    }
}




