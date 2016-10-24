package com.dcits.smartbip.reversal.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.reversal.dao.BipCompositeTransDAO;
import com.dcits.smartbip.reversal.dao.BipReversalInfoDAO;
import com.dcits.smartbip.reversal.entity.BipCompositeTransEntity;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;

/**
 * Created by vincentfxz on 16/5/6.
 */
@Service
@Transactional
public class BipReversalInfoService extends ReversalBaseService<BipReversalInfoEntity> {
    @Autowired
    private BipReversalInfoDAO bipReversalInfoDAO;

    @Override
    public BipReversalInfoDAO getRepository() {
        return bipReversalInfoDAO;
    }

}
