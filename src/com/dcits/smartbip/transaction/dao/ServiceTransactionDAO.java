package com.dcits.smartbip.transaction.dao;

import com.dcits.smartbip.transaction.entity.ServiceTransaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by vincentfxz on 16/9/23.
 */
public interface ServiceTransactionDAO extends CrudRepository<ServiceTransaction, String> {
}
