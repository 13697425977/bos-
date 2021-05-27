package com.itheima.bos.dao.impl;

import com.itheima.bos.dao.BaseDao;
import com.itheima.bos.utils.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    //代表某个实体的类型
    private Class<T> entityclass;

    public BaseDaoImpl(){
        ParameterizedType superclass= (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = superclass.getActualTypeArguments();
        entityclass= (Class<T>) actualTypeArguments[0];
    }

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    @Override
    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public T findById(Serializable id) {
        return this.getHibernateTemplate().get(entityclass,id);
    }

    @Override
    public List<T> findAll() {
        String hql="FROM "+entityclass.getSimpleName();
        return (List<T>) this.getHibernateTemplate().find(hql);
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    //执行更新
    @Override
    public void executeUpdate(String queryName, Object... objects) {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.getNamedQuery(queryName);
        int i=0;
        for(Object o:objects){
            query.setParameter(i++,o);
        }
        //执行更新
        query.executeUpdate();
    }

    //通用分页方法
    @Override
    public void pageQuery(PageBean pageBean) {
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize();
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();

        //查询total总数据量
        detachedCriteria.setProjection(Projections.rowCount());
        List<Long> lists = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
        Long count = lists.get(0);
        pageBean.setTotal(count.intValue());

        //查询rows当前页需要展示的数据集合
        detachedCriteria.setProjection(null);
        //指定hibernate框架封装对象的方式
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        int firstResult=(currentPage-1)*pageSize;
        int maxResult=pageSize;
        List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);
        pageBean.setRows(rows);
    }
}
