package com.teljjb.dao;

import java.util.List;
import java.lang.Integer;
import org.apache.ibatis.annotations.Param;
import com.teljjb.bean.Plan;

/**
 *
 * This tools just a simple useful tools, gen table to javabean
 *
 * "I hope this tools can save your time"
 *
 * Generated by <tt>Generate</tt> 
 *
 * @author com.ypn.mapi.generate
 * @version v1.0
 */
public interface PlanDao {

	public Plan findPlanById(Integer id);
	
	public Integer insert(Plan param);
	
	public Integer getPlanCount(Plan param);
	
	public List<Plan> findPlansByPage(@Param("param")Plan param,@Param("offset")Integer offset,@Param("rows")Integer rows);
	
	//if not use,pls delete it
	public Integer deletePlanById(Integer id);
}