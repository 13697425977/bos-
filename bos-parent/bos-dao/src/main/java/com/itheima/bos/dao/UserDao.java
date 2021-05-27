package com.itheima.bos.dao;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{

    User findUserByUsername(String username);

    List<Function> findFunctionsByUserName(String username);
}
