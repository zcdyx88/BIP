package com.dcits.smartbip.buss.accountopen.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcits.smartbip.buss.accountopen.entity.BipTransAccountOpen;

public interface BipTransAccountOpenDAO extends CrudRepository<BipTransAccountOpen, String> {

	/**
	 * 根据交易流水号查询
	 * @param jiaoyils
	 * @return
	 */
	@Query("select t.dkczhzhh from BipTransAccountOpen t where t.jiaoyils = ?1")
	public String getDkczhzhhByJiaoyils(java.lang.String jiaoyils);
	
	/**
	 * 根据外部流水号查询
	 * @param waiblius
	 * @return
	 */
	@Query("select t.dkczhzhh from BipTransAccountOpen t where t.waiblius = ?1")
	public String getDkczhzhhByWaiblius(java.lang.String waiblius);
	
}
