package com.dcits.smartbip.reversal.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.reversal.dao.BipReversalInfoDAO;
import com.dcits.smartbip.reversal.dao.BipReversalInfoHistoryDAO;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;
import com.dcits.smartbip.reversal.entity.BipReversalInfoHistoryEntity;

/**
 * Created by vincentfxz on 16/5/6.
 */
@Service
@Transactional
public class BipReversalInfoHistoryService extends ReversalBaseService<BipReversalInfoHistoryEntity> {
    @Autowired
    private BipReversalInfoHistoryDAO bipReversalInfoHistoryDAO;

    @Override
    public BipReversalInfoHistoryDAO getRepository() {
        return bipReversalInfoHistoryDAO;
    }

}
