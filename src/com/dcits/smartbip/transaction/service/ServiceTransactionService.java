package com.dcits.smartbip.transaction.service;

import com.dcits.smartbip.journal.dao.BipTranLogDAO;
import com.dcits.smartbip.journal.entity.BipTranLog;
import com.dcits.smartbip.journal.service.BaseService;
import com.dcits.smartbip.transaction.dao.ServiceTransactionDAO;
import com.dcits.smartbip.transaction.entity.ServiceTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by vincentfxz on 16/9/23.
 */
@Service
@Transactional
public class ServiceTransactionService extends BaseService<ServiceTransaction> {
    @Autowired
    private ServiceTransactionDAO serviceTransactionDAO;

    @Override
    public CrudRepository getRepository() {
        return serviceTransactionDAO;
    }

}
