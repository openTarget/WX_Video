package com.lhn.common.persistence;

import com.lhn.utils.MyMapper;

import java.util.List;

/**
 * DAO支持类实现
 * 
 * @param <T>
 */
public interface CrudDao<T> extends MyMapper<T> {

    /**
     * 获取单条数据
     * 
     * @param id
     * @return
     */
    public T get(String id);

    /**
     * 获取单条数据
     * 
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * 
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 插入数据
     * 
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 更新数据
     * 
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 更新排序
     * 
     * @param entity
     * @return
     */
    public void updateSort(T entity);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * 
     * @param entity
     * @return
     */
    @Override
    public int delete(T entity);

}
