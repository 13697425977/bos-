package com.itheima.bos.web.action;

import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    PageBean pageBean=new PageBean();

    //模型对象
    protected T model;

    //在构造方法中动态获取实体类型,通过反射创建model对象
    public BaseAction(){
        ParameterizedType parameterizedType= (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        Class<T> clazz= (Class<T>) types[0];
        detachedCriteria=DetachedCriteria.forClass(clazz);
        pageBean.setDetachedCriteria(detachedCriteria);
        try{
            model=clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //创建离线查询对象
    DetachedCriteria detachedCriteria=null;

    public void setPage(int page){
        pageBean.setCurrentPage(page);
    }

    public void setRows(int rows){
        pageBean.setPageSize(rows);
    }

    @Override
    public T getModel() {
        return model;
    }

    //将java对象转化为json，并响应到客户端
    public void java2Json(Object object,String[] excludes){
        JsonConfig jsonConfig = new JsonConfig();
        //指定哪些属性不需要转json
        String s=null;
        jsonConfig.setExcludes(excludes);

        if(object instanceof Collection){
            s= JSONArray.fromObject(object,jsonConfig).toString();
        }else{
            s=JSONObject.fromObject(object, jsonConfig).toString();
        }
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try{
            ServletActionContext.getResponse().getWriter().print(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
