package com.dcits.smartbip.common.model;

import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.InstanceNotFoundException;

import java.util.List;

/**
 * Created by vincentfxz on 16/4/18.
 * 所有的容器单元的接口
 */
public interface IRepository<T, PK> {
    /**
     * 从容器获取一个注册对象
     * @param id
     * @return
     * @throws InstanceNotFoundException
     */
    T get(PK id) throws InstanceNotFoundException;

    /**
     * 从配置文件导入注册对象
     * @param configurations
     * @throws ConfigurationNotFoundException
     */
    void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException;

    /**
     * 注册一个对象
     * @param id
     * @param t
     */
    void register(PK id, T t);

    /**
     * 注销一个注册对象
     * @param id
     */
    void remove(PK id);

    /**
     * 持久化一个注册对象
     * @param id
     */
    void persist(PK id);

    /**
     * 获取容器中的数量
     * @return
     */
    int size();

}
