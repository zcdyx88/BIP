package com.dcits.smartbip.reversal.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.reversal.dao.BipCompositeTransDAO;
import com.dcits.smartbip.reversal.dao.BipReversalInfoDAO;
import com.dcits.smartbip.reversal.dao.BipReversalPolicyDAO;
import com.dcits.smartbip.reversal.entity.BipCompositeTransEntity;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;
import com.dcits.smartbip.reversal.entity.BipReversalPolicyEntity;

/**
 * Created by vincentfxz on 16/5/6.
 */
@Service
@Transactional
public class BipReversalPolicyService extends ReversalBaseService<BipReversalPolicyEntity> {
    @Autowired
    private BipReversalPolicyDAO bipReversalPolicyDAO;

    @Override
    public CrudRepository getRepository() {
        return bipReversalPolicyDAO;
    }

}
