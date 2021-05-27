package com.itheima.bos.web.action;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

    @Autowired
    private RoleService roleService;

    private String functionids;
    //用户添加方法
    public String add(){
        try{
            roleService.add(model,functionids);
        }catch (Exception e){
            e.printStackTrace();
            this.addActionError("角色添加失败");
            return null;
        }
        return "list";
    }

    //用户查询方法
    public String findAll(){
        List<Role> lists=roleService.findAll();
        java2Json(lists,new String[]{"functions","users"});
        return NONE;
    }

    //用户修改方法
    public String update(){
        try{
            roleService.update(model);
        }catch (Exception e){
            e.printStackTrace();
            this.addActionError("角色修改失败");
            return null;
        }
        return "list";
    }

    private String ids;

    //用户删除方法
    public void delete(){
        try{
            roleService.delete(ids);
            ServletActionContext.getResponse().getWriter().print(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setFunctionids(String functionids) {
        this.functionids = functionids;
    }
}
