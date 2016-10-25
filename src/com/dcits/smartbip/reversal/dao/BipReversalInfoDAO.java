package com.dcits.smartbip.reversal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcits.smartbip.reversal.entity.BipBuszzTransEntity;
import com.dcits.smartbip.reversal.entity.BipReversalInfoEntity;
import com.dcits.smartbip.reversal.entity.BipReversalPolicyEntity;

/**
 * Created by zhangcheng on 16/8/27.
 */
public interface BipReversalInfoDAO extends CrudRepository<BipReversalInfoEntity, String> {
	
	@Query("select s from BipReversalInfoEntity s where s.count < ?1 and s.reversalResult = -1")
	List<BipReversalInfoEntity> queryReverInfo(int maxCount);
	
	
	
}
