package com.itheima.bos.dao.impl;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User findUserByUsername(String username) {
        String hql="FROM User u where u.username = ?";
        List list = this.getHibernateTemplate().find(hql, username);
        if(list!=null&&list.size()>0){
            return (User) list.get(0);
        }
        return null;
    }

    public List<Function> findFunctionsByUserName(String username){
       String hql="select distinct f from Function f left outer join f.roles r"+
               " left outer join r.users u where f.generatemenu = 1 and u.username = ? order by f.zindex asc ";
        return (List<Function>) this.getHibernateTemplate().find(hql, username);
    }
}
