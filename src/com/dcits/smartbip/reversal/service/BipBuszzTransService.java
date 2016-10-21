package com.dcits.smartbip.reversal.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.reversal.dao.BipBuszzTransDAO;
import com.dcits.smartbip.reversal.dao.BipCompositeTransDAO;
import com.dcits.smartbip.reversal.entity.BipBuszzTransEntity;
import com.dcits.smartbip.reversal.entity.BipCompositeTransEntity;

/**
 * Created by zhangcheng on 16/5/6.
 */
@Service
@Transactional
public class BipBuszzTransService extends ReversalBaseService<BipBuszzTransEntity> {
    @Autowired
    private BipBuszzTransDAO bipBuszzTransDAO;

    @Override
    public CrudRepository getRepository() {
        return bipBuszzTransDAO;
    }

}
