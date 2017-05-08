package com.teljjb.service.api;

import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserResult;

/**
 * Created by dezhonger on 2017/5/8.
 */
public interface UserService {

    UserResult findUserResultById(Integer id) throws BusinessException;

    UserResult register(String nickname, String phonenumber, String email, String password, String ip) throws
            BusinessException;

    /**
     * 上传头像
     * @param headImage
     * @param userId
     * @return
     * @throws BusinessException
     */
    public Boolean uploadHeadImage(String headImage, Integer userId) throws BusinessException;

}
