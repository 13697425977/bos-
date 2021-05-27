package com.itheima.bos.service.impl;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.utils.Menu;
import com.itheima.bos.utils.UUIDUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionDao functionDao;

    @Override
    public void add(Function function) {
        function.setId(UUIDUtils.getString16());
        functionDao.save(function);
    }

    @Override
    public List<Function> findAll() {
        return functionDao.findAll();
    }

    @Override
    public List<Menu> findAllMenu() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
        detachedCriteria.add(Restrictions.eq("generatemenu","1"));
        detachedCriteria.addOrder(Order.asc("zindex"));
        List<Function> list = functionDao.findByCriteria(detachedCriteria);
        List<Menu> menuList=new ArrayList<>();
        for(Function function:list){
            Menu menu=new Menu();
            menu.setId(function.getId());
            menu.setName(function.getName());
            menu.setPage(function.getPage());
            if(function.getParentFunction()!=null){
                menu.setpId(function.getParentFunction().getId());
            }else{
                menu.setpId(null);
            }
            menuList.add(menu);
        }
        return menuList;
    }
}
