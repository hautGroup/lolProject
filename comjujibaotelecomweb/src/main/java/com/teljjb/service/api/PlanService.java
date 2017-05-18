package com.teljjb.service.api;

import com.teljjb.exception.BusinessException;
import com.teljjb.result.PlanResult;

import java.util.Date;

/**
 * Created by Dezhonger on 2017/5/18.
 */
public interface PlanService {

    PlanResult addPlain(Integer userId, String topic, String synopsis, String content, Date endTime) throws BusinessException;
}
