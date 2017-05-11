package com.teljjb.service.impl;

import com.teljjb.bean.UserSignInRecord;
import com.teljjb.dao.UserSignInRecordDao;
import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserSignInRecordResult;
import com.teljjb.service.api.SignInService;
import com.teljjb.util.BeanUtilExt;
import com.teljjb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dezhonger on 2017/5/11.
 */
@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserSignInRecordDao userSignInRecordDao;

    @Override
    public UserSignInRecordResult playSignIn(Integer userId) throws BusinessException {
        UserSignInRecord record = userSignInRecordDao.findUserSignInRecordByTime(DateUtil.dateOnlyExt(new Date()));
        if(record != null) {
            throw new BusinessException(-9998, "您今天已经签过到了!");
        }

        Date date = DateUtil.addDays(DateUtil.dateOnlyExt(new Date()), -1);
        UserSignInRecord userSignInRecord0 = userSignInRecordDao.findUserSignInRecordByTime(date);
        UserSignInRecord userSignInRecord = new UserSignInRecord();
        userSignInRecord.setUserId(userId);
        userSignInRecord.setCreateTime(new Date());
        userSignInRecord.setUpdateTime(new Date());
        userSignInRecord.setSeriesSignedTimes(userSignInRecord0 == null ? 1 : userSignInRecord0.getSeriesSignedTimes()
                + 1);
        userSignInRecordDao.insert(userSignInRecord);
        UserSignInRecordResult result = new UserSignInRecordResult();
        BeanUtilExt.copyProperties(result, userSignInRecord);
        return result;
    }
}
