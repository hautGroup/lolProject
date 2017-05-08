package com.teljjb.service.api;

import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserResult;

/**
 * Created by dezhonger on 2017/5/8.
 */
public interface UserService {

    UserResult findUserResultById(Integer id) throws BusinessException;

    UserResult register(String nickname, String phonenumber, String email, String password) throws BusinessException;

}
