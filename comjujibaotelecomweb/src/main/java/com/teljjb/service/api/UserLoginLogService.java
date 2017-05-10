package com.teljjb.service.api;

import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserLoginLogResult;

/**
 * Created by dezhonger on 2017/5/10.
 */
public interface UserLoginLogService {

    UserLoginLogResult userLoginLog(Integer userId, String platform, String deviceNumber, String ip) throws
            BusinessException;
}
