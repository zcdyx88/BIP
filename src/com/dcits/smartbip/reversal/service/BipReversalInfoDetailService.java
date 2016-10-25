package com.dcits.smartbip.reversal.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.reversal.dao.BipReversalInfoDetailDAO;
import com.dcits.smartbip.reversal.entity.BipReversalInfoDetailEntity;

/**
 * Created by zhangcheng on 16/5/6.
 */
@Service
@Transactional
public class BipReversalInfoDetailService extends ReversalBaseService<BipReversalInfoDetailEntity> {
    @Autowired
    private BipReversalInfoDetailDAO bipReversalInfoDetailDAO;

    @Override
    public BipReversalInfoDetailDAO getRepository() {
        return bipReversalInfoDetailDAO;
    }

}
