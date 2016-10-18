package com.dcits.smartbip.runtime.dao;

import java.util.List;

import javax.persistence.Table;
import com.dcits.smartbip.runtime.entity.BipProductsTab;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vincentfxz on 16/8/8.
 */
@Repository
@Table(name="BIP_PRODUCTS_TAB")
@Qualifier("bipProductsTabDAO")
public interface BipProductsTabDAO extends CrudRepository<BipProductsTab, String> {
	
	@Modifying
	@Query("delete from BipProductsTab t where t.SignVarCd=?1 and t.PrivyCorpFlg=?2")
	public int deleteById(String signVarCd,String privyCorpFlg);
	
	@Query("select t from BipProductsTab t")
	public List<BipProductsTab> getAllProducts();
}
