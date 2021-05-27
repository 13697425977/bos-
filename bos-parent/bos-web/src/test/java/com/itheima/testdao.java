package com.itheima;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.dao.impl.UserDaoImpl;
import com.itheima.bos.domain.Function;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class testdao {

    @Test
    public void test1(){
        ClassPathXmlApplicationContext cpa = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao ud = cpa.getBean(UserDaoImpl.class);
        List<Function> list = ud.findFunctionsByUserName("8afc01e00df940e");
        System.out.println(list.get(0).getName());
    }
}
