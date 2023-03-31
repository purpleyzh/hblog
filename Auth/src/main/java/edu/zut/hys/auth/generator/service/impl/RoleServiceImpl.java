package edu.zut.hys.auth.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.auth.generator.service.RoleService;
import edu.zut.hys.auth.generator.mapper.RoleMapper;
import edu.zut.hys.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    @Cacheable(value = "role",keyGenerator = "keyGenerator")
    @Override
    public List<Role> listByRoleIds(List<Long> ids) {
        return roleMapper.selectBatchIds(ids);
    }
}




