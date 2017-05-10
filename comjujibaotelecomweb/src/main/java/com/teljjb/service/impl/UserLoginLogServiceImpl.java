package com.teljjb.service.impl;

import com.teljjb.bean.UserLoginLog;
import com.teljjb.dao.UserLoginLogDao;
import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserLoginLogResult;
import com.teljjb.service.api.UserLoginLogService;
import com.teljjb.util.BeanUtilExt;
import com.teljjb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dezhonger on 2017/5/10.
 */
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Override
    public UserLoginLogResult userLoginLog(Integer userId, String platform, String deviceNumber, String ip) throws BusinessException {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setDeviceNumber(deviceNumber);
        if(StringUtil.isEmpty(platform)) {
            platform = "wap";
        }
        userLoginLog.setPlatform(platform);
        userLoginLog.setLoginIp(ip);
        userLoginLog.setCreateTime(new Date());
        userLoginLog.setUpdateTime(new Date());
        System.out.println("userLoginLog");
        System.out.println(userLoginLogDao.insert(userLoginLog));
        UserLoginLogResult userLoginLogResult = new UserLoginLogResult();
        BeanUtilExt.copyProperties(userLoginLogResult, userLoginLog);
        return userLoginLogResult;
    }
}
