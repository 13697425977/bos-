package com.itheima.bos.realm;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class BOSRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("自定义的realm方法执行了.....");
        UsernamePasswordToken passwordToken= (UsernamePasswordToken) token;
        //获取页面输入的用户名
        String username = passwordToken.getUsername();
        //根据用户名查询数据库中的密码
        User user=userDao.findUserByUsername(username);
        if(user==null){
            return null;
        }
        //简单认证信息对象
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;
    }
}
