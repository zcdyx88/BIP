package com.dcits.smartbip.reversal.dao;

import com.dcits.smartbip.reversal.entity.BipReversalPolicy;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by vincentfxz on 16/8/27.
 */
public interface BipReversalPolicyDAO extends CrudRepository<BipReversalPolicy, String> {
}
