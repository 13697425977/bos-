package com.itheima.bos.service;

import com.itheima.bos.domain.Role;

import java.util.List;

public interface RoleService {
    void add(Role role,String functionids);

    List<Role> findAll();

    void update(Role role);

    void delete(String ids);
}
