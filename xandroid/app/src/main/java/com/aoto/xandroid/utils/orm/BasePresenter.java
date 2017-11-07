package com.aoto.xandroid.utils.orm;

import com.aoto.xandroid.utils.Tools;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * 基础Dao实现基本增删改查
 */

public class BasePresenter {
    public DbManager db = DbHelper.initDB();

    /**
     * 保存实体
     *
     * @param entity
     * @param <T>
     * @return
     */
    public <T> T saveEntity(T entity) {
        if (entity != null) {
            try {
                boolean rs = db.saveBindingId(entity);
                if (rs) {
                    return entity;
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询所有实体
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> queryEntitys(Class<T> clazz) {
        if (clazz != null) {
            try {
                Selector<T> selector = db.selector(clazz);
                if (selector != null) {
                    List<T> rs = selector.findAll();
                    if (rs != null) {
                        return rs;
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询实体分页
     *
     * @param page
     * @param pageSize
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> queryEntitysByPage(Integer page, Integer pageSize, Class<T> clazz) {
        if (clazz != null && page != null && pageSize != null) {
            try {
                Selector<T> selector = db.selector(clazz);
                if (selector != null) {
                    selector = selector.offset(pageSize * (page - 1)).limit(pageSize);
                    List<T> rs = selector.findAll();
                    if (rs != null) {
                        return rs;
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据筛选条件查询实体
     *
     * @param filter
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> queryEntityByFilters(Class<T> clazz, T filter) {
        if (filter != null && clazz != null) {
            try {
                Selector<T> selector = db.selector(clazz);
                if (selector != null) {
                    String whereExpr = DbHelper.buildWhereExpr(filter);
                    if (Tools.isNotBlank(whereExpr)) {
                        List<T> rs = selector.expr(whereExpr).findAll();
                        if (rs != null) {
                            return rs;
                        }
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 修改实体
     * @param clazz
     * @param filter
     * @param value
     * @param <T>
     * @return
     */
    public <T> int modifyEntity(Class<T> clazz, T filter, KeyValue... value) {
        if (filter != null && clazz != null) {
            try {
                WhereBuilder whereBuilder = DbHelper.buildWhere(filter);
                if (whereBuilder != null) {
                    int rs = db.update(clazz, whereBuilder, value);
                    if(rs>=0){
                        return rs;
                    }
                }
            } catch (DbException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 根据主键修改实体
     * @param clazz
     * @param filter
     * @param value
     * @param <T>
     * @return
     */
    public <T> int modifyEntityById(Class<T> clazz, Integer filter, KeyValue... value) {
        if (filter != null && clazz != null) {
            try {
                WhereBuilder whereBuilder = WhereBuilder.b().expr("id=" + filter);
                if (whereBuilder != null) {
                    int rs = db.update(clazz, whereBuilder, value);
                    if(rs>=0){
                        return rs;
                    }
                }
            } catch (DbException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 删除实体
     * @param clazz
     * @param filter
     * @param <T>
     * @return
     */
    public <T> int deleteEntity(Class<T> clazz,T filter){
        if (filter != null && clazz != null) {
            try {
                WhereBuilder whereBuilder = DbHelper.buildWhere(filter);
                if (whereBuilder != null) {
                    int rs = db.delete(clazz, whereBuilder);
                    if(rs>=0){
                        return rs;
                    }
                }
            } catch (DbException e){
                e.printStackTrace();
            }
        }
        return 0;
    }
    /**
     * 根据主键删除实体
     * @param clazz
     * @param filter
     * @param <T>
     * @return
     */
    public <T> int deleteEntityById(Class<T> clazz,Integer filter){
        if (filter != null && clazz != null) {
            try {
                WhereBuilder whereBuilder = WhereBuilder.b().expr("id=" + filter);
                if (whereBuilder != null) {
                    int rs = db.delete(clazz, whereBuilder);
                    if(rs>=0){
                        return rs;
                    }
                }
            } catch (DbException e){
                e.printStackTrace();
            }
        }
        return 0;
    }
}
