package com.dcits.smartbip.reversal.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.reversal.dao.BipCompositeTransDAO;
import com.dcits.smartbip.reversal.entity.BipCompositeTransEntity;

/**
 * Created by vincentfxz on 16/5/6.
 */
@Service
@Transactional
public class BipCompositeTransService extends ReversalBaseService<BipCompositeTransEntity> {
    @Autowired
    private BipCompositeTransDAO bipCompositeTransDAO;

    @Override
    public CrudRepository getRepository() {
        return bipCompositeTransDAO;
    }

}
