package com.dcits.smartbip.reversal.service;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by vincentfxz on 16/5/6.
 */
public abstract class ReversalBaseService<T> {
    public Iterable<T> getAll(){
        return getRepository().findAll(); 
    }

    public T findOne(String id){
        return (T)getRepository().findOne(id);
    }


    public void save(T t){
        getRepository().save(t);
    }

    public void delete(T t){
        getRepository().delete(t);
    }

    public abstract CrudRepository getRepository();


}
