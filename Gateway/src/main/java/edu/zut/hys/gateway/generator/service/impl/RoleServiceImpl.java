package edu.zut.hys.gateway.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.Role;
import edu.zut.hys.gateway.generator.service.RoleService;
import edu.zut.hys.gateway.generator.mapper.RoleMapper;
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

    @Cacheable(value = "role", keyGenerator = "keyGenerator")
    @Override
    public List<Role> getRolesByIds(List<Long> ids) {
        return roleMapper.selectBatchIds(ids);
    }
}




