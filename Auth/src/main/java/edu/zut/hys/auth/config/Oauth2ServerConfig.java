package edu.zut.hys.auth.config;

import edu.zut.hys.auth.component.JwtTokenEnhancer;
import edu.zut.hys.auth.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 * Created by hys on 2022/1/30.
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    //密码加密
    private final PasswordEncoder passwordEncoder;
    //用户信息
    private final UserDetailsServiceImpl userDetailsService;
    //权限管理
//    在Spring Security中，AuthenticationManager的默认实现是ProviderManager，而且它不直接自己处理认证请求，
//    而是委托给其所配置的AuthenticationProvider列表，然后会依次使用每一个AuthenticationProvider进行认证，
//    如果有一个AuthenticationProvider认证后的结果不为null，则表示该AuthenticationProvider已经认证成功，
//    之后的AuthenticationProvider将不再继续认证。然后直接以该AuthenticationProvider的认证结果作为ProviderManager的认证结果。
//    如果所有的AuthenticationProvider的认证结果都为null，则表示认证失败，将抛出一个ProviderNotFoundException。
//    校验认证请求最常用的方法是根据请求的用户名加载对应的UserDetails，然后比对UserDetails的密码与认证请求的密码是否一致，一致则表示认证通过。
//    Spring Security内部的DaoAuthenticationProvider就是使用的这种方式。
//    其内部使用UserDetailsService来负责加载UserDetails。
//    在认证成功以后会使用加载的UserDetails来封装要返回的Authentication对象，
//    加载的UserDetails对象是包含用户权限等信息的。认证成功返回的Authentication对象将会保存在当前的SecurityContext中。
    private final AuthenticationManager authenticationManager;
    //token内容增强，加入了用户id
    private final JwtTokenEnhancer jwtTokenEnhancer;

    //客户端信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                //支持密码与refresh_token获取新令牌
                .authorizedGrantTypes("password", "refresh_token")
                //令牌1小时过期
                .accessTokenValiditySeconds(3600)
                //刷新令牌24小时过期
                .refreshTokenValiditySeconds(86400);
    }

    //配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter());
        //配置JWT的内容增强器
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                //配置加载用户信息的服务
                .userDetailsService(userDetailsService)
                //签名配置
                .accessTokenConverter(accessTokenConverter())
                //添加额外信息
                .tokenEnhancer(enhancerChain);
    }

    //令牌安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单验证
        security.allowFormAuthenticationForClients();
    }

    //使用非对称加密算法来对Token进行签名
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    //加载密钥
    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }

}
