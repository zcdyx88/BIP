package com.dcits.smartbip.journal.service;

import com.dcits.smartbip.journal.dao.BipTranMsgDAO;
import com.dcits.smartbip.journal.entity.BipTranMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by vincentfxz on 16/8/8.
 */
@Service
@Transactional
public class BipTranMsgService extends BaseService<BipTranMsg> {
    @Autowired
    private BipTranMsgDAO bipTranMsgDAO;

    @Override
    public CrudRepository getRepository() {
        return bipTranMsgDAO;
    }
}
