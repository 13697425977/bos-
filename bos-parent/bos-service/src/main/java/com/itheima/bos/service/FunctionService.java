package com.itheima.bos.service;

import com.itheima.bos.domain.Function;
import com.itheima.bos.utils.Menu;

import java.util.List;

public interface FunctionService {
    void add(Function model);

    List<Function> findAll();

    List<Menu> findAllMenu();
}
