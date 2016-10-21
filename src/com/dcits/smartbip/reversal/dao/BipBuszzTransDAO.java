package com.dcits.smartbip.reversal.dao;

import org.springframework.data.repository.CrudRepository;

import com.dcits.smartbip.reversal.entity.BipBuszzTransEntity;
import com.dcits.smartbip.reversal.entity.BipReversalPolicyEntity;

/**
 * Created by zhangcheng on 16/8/27.
 */
public interface BipBuszzTransDAO extends CrudRepository<BipBuszzTransEntity, String> {
}
