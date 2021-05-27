package com.itheima.bos.web.action;

import com.itheima.bos.domain.User;
import com.itheima.bos.service.UserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.Menu;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{

    //接受页面提交的验证码
	//wenjian
    private String checkcode;

    @Autowired
    private UserService userService;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    //用户登录使用shiro框架提供的方式进行认证操作
    public String login(){
        //从session中获取生成的验证码
        Object validatecode = ServletActionContext.getRequest().getSession().getAttribute("key");
        //校验验证码是否输入正确
        if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validatecode)){
            //使用shiro框架提供的方式进行认证操作
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));
            try {
                subject.login(token);
            }catch (Exception e){
                e.printStackTrace();
                return LOGIN;
            }
            User user= (User) subject.getPrincipal();
            ServletActionContext.getRequest().getSession().setAttribute("loginUser",user);
            return "home";
        }else{
            //输入的验证码有误,提示错误信息，并跳转到登录页面
            this.addActionError("输入的验证码有误");
            return LOGIN;
        }
    }

    private List<String> rolelist;

    //用户添加方法
    public String add(){
        try{
            userService.add(model,rolelist);
        }catch (Exception e){
            e.printStackTrace();
            this.addActionError("用户添加失败");
            return null;
        }
        return "list";
    }

    //用户查询方法
    public String findAll(){
        List<User> lists=userService.findAll();
        java2Json(lists,new String[]{"noticebills","roles"});
        return NONE;
    }

    //用户修改方法
    public String update(){
        try{
            userService.update(model);
        }catch (Exception e){
            e.printStackTrace();
            this.addActionError("用户修改失败");
            return null;
        }
        return "list";
    }

    private String ids;

    //用户删除方法
    public void delete(){
        try{
            userService.delete(ids);
            ServletActionContext.getResponse().getWriter().print(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //根据登录用户加载左侧菜单的方法
    public String findMenuByUser(){
        User loginuser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        List<Menu> menus=userService.findMenuByUser(loginuser.getUsername());
        java2Json(menus,null);
        return NONE;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setRolelist(List<String> rolelist) {
        this.rolelist = rolelist;
    }
}
