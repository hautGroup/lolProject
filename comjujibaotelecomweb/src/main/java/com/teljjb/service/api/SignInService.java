package com.teljjb.service.api;

import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserSignInRecordResult;

/**
 * Created by dezhonger on 2017/5/11.
 */
public interface SignInService {

    UserSignInRecordResult playSignIn(Integer userId) throws BusinessException;
}
