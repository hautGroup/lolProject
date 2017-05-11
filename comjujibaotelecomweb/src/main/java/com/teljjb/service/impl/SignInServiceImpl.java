package com.teljjb.service.impl;

import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserSignInRecordResult;
import com.teljjb.service.api.SignInService;
import org.springframework.stereotype.Service;

/**
 * Created by dezhonger on 2017/5/11.
 */
@Service
public class SignInServiceImpl implements SignInService {

    @Override
    public UserSignInRecordResult playSignIn(Integer userId) throws BusinessException {
        return null;
    }
}
