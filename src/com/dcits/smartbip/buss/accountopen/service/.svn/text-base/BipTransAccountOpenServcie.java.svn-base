package com.dcits.smartbip.buss.accountopen.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.buss.accountopen.dao.BipTransAccountOpenDAO;
import com.dcits.smartbip.buss.accountopen.entity.BipTransAccountOpen;
import com.dcits.smartbip.journal.service.BaseService;

@Service
@Transactional
public class BipTransAccountOpenServcie extends BaseService<BipTransAccountOpen>{
    @Autowired
    public BipTransAccountOpenDAO bipTransAccountOpenDAO;
    @Autowired
    public CrudRepository getRepository() {
        return bipTransAccountOpenDAO;
    }
}
