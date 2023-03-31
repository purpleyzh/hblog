package edu.zut.hys.gateway.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.domain.Permission;
import edu.zut.hys.domain.Role;
import edu.zut.hys.domain.RolePermission;
import edu.zut.hys.gateway.constant.AuthConstant;
import edu.zut.hys.gateway.generator.service.impl.PermissionServiceImpl;
import edu.zut.hys.gateway.generator.service.impl.RolePermissionServiceImpl;
import edu.zut.hys.gateway.generator.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by hys on 2022/1/30.
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private PermissionServiceImpl permissionService;
    @Autowired
    private RolePermissionServiceImpl rolePermissionService;
    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        URI uri = authorizationContext.getExchange().getRequest().getURI();

        Permission permission = permissionService.getOneByResource(uri.getPath());
        if(permission == null){
            System.out.println("404 : NOT FOUND"+ uri.getPath());
            throw new NullPointerException("URI ERROR");
        }

        List<RolePermission> rolePermissions = rolePermissionService.getRolePermissionsByPermissionid(permission.getPermissionid());

        List<Long> roleids = new ArrayList<>();
        rolePermissions.forEach((item)->{
            roleids.add(item.getRoleid());
        });
        List<Role> roles =  roleService.getRolesByIds(roleids);

        List<String> authorities = new ArrayList<>();
        for (Role item : roles) {
            authorities.add(item.getRolename());
        }
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
