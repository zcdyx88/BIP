package com.dcits.smartbip.journal.service;


import com.dcits.smartbip.journal.dao.BipTranLogDAO;
import com.dcits.smartbip.journal.entity.BipTranLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by vincentfxz on 16/5/6.
 */
@Service
@Transactional
public class BipTranLogService extends BaseService<BipTranLog> {
    @Autowired
    private BipTranLogDAO bipTranLogDAO;

    @Override
    public CrudRepository getRepository() {
        return bipTranLogDAO;
    }

}
