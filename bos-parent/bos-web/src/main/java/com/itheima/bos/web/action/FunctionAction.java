package com.itheima.bos.web.action;

import com.itheima.bos.domain.Function;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.utils.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

    @Autowired
    private FunctionService functionService;

    //权限添加方法
    public String add(){
        try{
            functionService.add(model);
        }catch (Exception e){
            e.printStackTrace();
            this.addActionError("权限添加失败");
            return null;
        }
        return "list";
    }

    //权限查询方法
    public String findAll(){
        List<Function> lists=functionService.findAll();
        java2Json(lists,new String[]{"roles","parentFunction"});
        return NONE;
    }

    //查询所有菜单
    public String findAllMenu(){
        List<Menu> menus=functionService.findAllMenu();
        java2Json(menus,null);
        return NONE;
    }
}
