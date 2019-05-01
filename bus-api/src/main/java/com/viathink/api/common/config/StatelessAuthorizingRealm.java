package com.viathink.api.common.config;

import com.viathink.core.auth.bean.AuthTokenEntity;
import com.viathink.core.auth.service.AuthTokenService;
import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.dto.RolePermissionDto;
import com.viathink.core.user.dto.UserRolePermissionDto;
import com.viathink.core.user.service.UserService;
import com.viathink.core.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;

public class StatelessAuthorizingRealm extends AuthorizingRealm {

    @Value("${sys.token.secret}")
    private String secret;
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private AuthTokenService authTokenService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessAuthenticationToken;
    }
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //如果打印信息只执行一次的话，说明缓存生效了，否则不生效. --- 配置缓存成功之后，只会执行1次/每个用户，因为每个用户的权限是不一样的.
        System.out.println("MyShiroRealm.doGetAuthorizationInfo()");
        //这是shiro提供的.
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取到用户的权限信息.
        UserRolePermissionDto userInfo = (UserRolePermissionDto)principals.getPrimaryPrincipal();
        if(userInfo != null) {
            for(RolePermissionDto role:userInfo.getRoles()){
                //添加角色.
                authorizationInfo.addRole(role.getName());
                //添加权限.
                for(PermissionEntity p: role.getPermissions()){
                    authorizationInfo.addStringPermission(p.getName());
                }
            }
        }
        return authorizationInfo;
    }
    // 身份校验
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessAuthenticationToken statelessToken = (StatelessAuthenticationToken) token;
        String jwtToken = statelessToken.getToken();
        if(jwtToken == null || jwtToken.equals("")) {
            throw new AuthenticationException("token invalid");
        }
        HashMap<String, String> hashMap = JWTUtil.verify(jwtToken, secret);
        if (hashMap == null) {
            throw new AuthenticationException("token invalid");
        }
        AuthTokenEntity authTokenEntity = authTokenService.findAuthTokenById(hashMap.get("tokenId"));
        if (authTokenEntity == null) {
            throw new AuthenticationException("token invalid");
        }
        Long uid = Long.valueOf(hashMap.get("uId"));
        UserRolePermissionDto userInfo = userService.findUserRolePermissionById(uid);
        if (userInfo == null) {
            throw new AuthenticationException("token invalid");
        }
        userInfo.setTokenId(hashMap.get("tokenId"));
        return new SimpleAuthenticationInfo(userInfo, jwtToken, getName());
    }
}
