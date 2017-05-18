package com.teljjb.service.impl;

import com.teljjb.bean.Plan;
import com.teljjb.dao.PlanDao;
import com.teljjb.exception.BusinessException;
import com.teljjb.result.PlanResult;
import com.teljjb.service.api.PlanService;
import com.teljjb.util.BeanUtilExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Dezhonger on 2017/5/18.
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Override
    public PlanResult addPlain(Integer userId, String topic, String synopsis, String content, Date endTime) throws BusinessException {
        Plan plan = new Plan();
        plan.setContent(content);
        plan.setUserId(userId);
        plan.setTopic(topic);
        plan.setSynopsis(synopsis);
        plan.setEndTime(endTime);
        plan.setCreateTime(new Date());
        plan.setIsEnd(new Date().before(endTime) ? "N" : "Y");
        plan.setIsGiveup("N");
        plan.setUpdateTime(new Date());
        planDao.insert(plan);
        PlanResult result = new PlanResult();
        BeanUtilExt.copyProperties(result, plan);
        return result;
    }
}
