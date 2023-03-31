package edu.zut.hys.gateway.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.domain.Permission;
import edu.zut.hys.domain.Role;
import edu.zut.hys.domain.RolePermission;
import edu.zut.hys.gateway.generator.mapper.PermissionMapper;
import edu.zut.hys.gateway.generator.mapper.RoleMapper;
import edu.zut.hys.gateway.generator.mapper.RolePermissionMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 网关白名单配置
 * Created by hys on 2022/1/30.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
//@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    Log log = LogFactory.get(this.getClass());
    private List<String> urls;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionMapper permissionMapper;

    public List<String> getUrls() {
        if(urls == null){
            urls = new ArrayList<>();

            Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("rolename","PUBLIC"));

            QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
            rolePermissionQueryWrapper.eq("roleid", role.getRoleid());
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);

            List<Long> permissionids = new LinkedList<>();
            for (RolePermission rolePermission : rolePermissions) {
                permissionids.add(rolePermission.getPermissionid());
            }

            List<Permission> permissions = permissionMapper.selectBatchIds(permissionids);

            for (Permission permission : permissions) {
                urls.add(permission.getResource());
                log.log(Level.INFO, permission.getResource());
            }
        }
        return urls;
    }
}
