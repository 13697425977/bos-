package com.itheima.bos.service.impl;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.UserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.Menu;
import com.itheima.bos.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void add(User user,List<String> rolelist) {
        user.setId(UUIDUtils.getString16());
        user.setPassword(MD5Utils.md5(user.getPassword()));
        for(String id:rolelist){
            Role role = roleDao.findById(id);
            user.getRoles().add(role);
        }
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        if(user!=null&&user.getBirthday()==null){
            System.out.println("将birthdaystr转化为birthday");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date date = sd.parse(user.getBirthdayStr());
                user.setBirthday(date);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        userDao.update(user);
    }

    @Override
    public void delete(String ids) {
        String[] ids_arr = ids.split(",");
        for(String id:ids_arr){
            User user = userDao.findById(id);
            userDao.delete(user);
        }
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public List<Menu> findMenuByUser(String username) {
        List<Function> functions = userDao.findFunctionsByUserName(username);
        List<Menu> menuList=new ArrayList<>();
        for(Function function:functions){
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
