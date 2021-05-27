package com.itheima.bos.service;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.Menu;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void add(User model,List<String> rolelist);

    void update(User model);

    void delete(String ids);

    User findById(String id);

    List<Menu> findMenuByUser(String username);
}
