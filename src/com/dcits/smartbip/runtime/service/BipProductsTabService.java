package com.dcits.smartbip.runtime.service;


import java.util.List;

import com.dcits.smartbip.journal.service.BaseService;
import com.dcits.smartbip.runtime.dao.BipProductsTabDAO;
import com.dcits.smartbip.runtime.entity.BipProductsTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * Created by vincentfxz on 16/5/6.
 */
@Service
@Transactional
public class BipProductsTabService extends BaseService<BipProductsTab> {
    @Autowired
    private BipProductsTabDAO bipProductsTabDAO;

    @Override
    public CrudRepository<BipProductsTab, String> getRepository() {
        return bipProductsTabDAO;
    }

	public List<BipProductsTab> getAllProduicts() {
		
		return ((BipProductsTabDAO)getRepository()).getAllProducts();
	}

	@Override
	public void delete(BipProductsTab t) {
		// TODO Auto-generated method stub
		super.delete(t);
	}

	@Override
	public void save(BipProductsTab t) {
		// TODO Auto-generated method stub
		super.save(t);
	}
	
	public int deleteById(String signVarCd,String privyCorpFlg){
		return ((BipProductsTabDAO)getRepository()).deleteById(signVarCd, privyCorpFlg);
	}
}
