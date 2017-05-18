package com.teljjb.service.impl;

import com.teljjb.bean.User;
import com.teljjb.dao.UserDao;
import com.teljjb.dao.UserLoginLogDao;
import com.teljjb.entity.Constant;
import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by dezhonger on 2017/5/8.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Override
    public UserResult findUserResultById(Integer id) throws BusinessException {
        User user = userDao.findUserById(id);
        UserResult userResult = new UserResult();
        if(user != null) {
            BeanUtilExt.copyProperties(userResult, user);
        } else {
            throw new BusinessException(-9997, "用户不存在");
        }
        return userResult;
    }

    @Override
    public UserResult findUserResultByNickname(String nickname) {
        User user = userDao.findUserByNickname(nickname);
        if(user != null) {
            UserResult userResult = new UserResult();
            BeanUtilExt.copyProperties(userResult, user);
            return userResult;
        }
        return null;
    }

    @Override
    @Transactional
    public UserResult register(String nickname, String phonenumber, String email, String password, String ip) throws
            BusinessException {
        if (StringUtils.isEmpty(phonenumber) || !PhoneNumerRegexUtil.isPhoneLegal(phonenumber)) {
            throw new BusinessException(-9997, "手机号为空或格式不正确");
        }
        if (StringUtils.isEmpty(password) || !PasswordUtil.checkPassword(password)) {
            throw new BusinessException(-9996, "密码为空或格式不正确");
        }
        if (StringUtils.isEmpty(email) || !EmailRegexUtil.checkEmaile(email)) {
            throw new BusinessException(-9995, "邮箱为空或格式不正确");
        }
        if (StringUtils.isEmpty(nickname) || !NicknameUtil.checkNickname(nickname)) {
            throw new BusinessException(-9994, "昵称为空或格式不正确");
        }

        User user = userDao.findUserByNicknameForupdate(nickname);
        if(user != null) {
            throw new BusinessException(-9993, "nickname已存在");
        }

        User user1 = new User();
        user1.setPassword(MD5Util.getMD5Str(Constant.PREMD5 + password));
        user1.setCreateTime(new Date());
        user1.setUpdateTime(new Date());
        user1.setBindPhone(phonenumber);
        user1.setEmail(email);
        user1.setHeadImage("");
        user1.setIsNotfiySign("N");
        user1.setLastLoginIp(ip);
        user1.setLastLoginTime(new Date());
        user1.setNickname(nickname);
        user1.setStatus("lock");
        userDao.insert(user1);
        UserResult userResult = new UserResult();
        BeanUtilExt.copyProperties(userResult, user1);
        return userResult;
    }

    @Override
    public UserResult findUserResultByEmail(String Email)  {
        User user = userDao.findUserByEmailForupdate(Email);
        if(user != null) {
            UserResult userResult = new UserResult();
            BeanUtilExt.copyProperties(userResult, user);
            return userResult;
        }
        return null;
    }

    @Override
    public Integer activeAccount(Integer id, String status) {
        return userDao.updateStatus(id, status);
    }


    @Override
    public UserResult userLogin(String nickname, String password) throws BusinessException {
        User user = userDao.findUserByNicknameAndPassword(nickname, password);
        if(user == null) {
            throw new BusinessException(-9999, "用户名或密码错误");
        } else {
            UserResult userResult = new UserResult();
            BeanUtilExt.copyProperties(userResult, user);
            return userResult;
        }
    }

    @Override
    public Boolean changeSigninStatus(Integer id, String status) throws BusinessException{
        Integer cnt = userDao.updateSigninStatus(id, status);
        return cnt > 0;
    }

    @Override
    public UserResult findUserByIdAndPass(Integer userId, String password) throws BusinessException {
        User user = userDao.findUserByUserIdAndPassword(userId, password);
        if(user == null) {
            throw new BusinessException(-9999, "密码已失效，请重新登录");
        } else {
            UserResult userResult = new UserResult();
            BeanUtilExt.copyProperties(userResult, user);
            return userResult;
        }
    }

    @Override
    public Boolean uploadHeadImage(String headImage, Integer userId) throws BusinessException {
        if (userDao.updateHeadImage(userId, headImage) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
