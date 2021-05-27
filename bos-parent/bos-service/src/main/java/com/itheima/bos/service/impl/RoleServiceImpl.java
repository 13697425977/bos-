package com.itheima.bos.service.impl;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FunctionDao functionDao;

    @Override
    public void add(Role role,String functionids) {
        for(String id:functionids.split(",")){
            Function function = functionDao.findById(id);
            role.getFunctions().add(function);
        }
        role.setId(UUIDUtils.getString16());
        roleDao.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void delete(String ids) {
        String[] ids_arr = ids.split(",");
        for(String id:ids_arr){
           Role role= roleDao.findById(id);
            roleDao.delete(role);
        }
    }
}
