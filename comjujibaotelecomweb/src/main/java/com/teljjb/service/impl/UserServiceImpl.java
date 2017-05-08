package com.teljjb.service.impl;

import com.teljjb.bean.User;
import com.teljjb.dao.UserDao;
import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.BeanUtilExt;
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

    @Override
    public UserResult findUserResultById(Integer id) throws BusinessException {
        User user = userDao.findUserById(id);
        UserResult userResult = new UserResult();
        if(user != null) {
            BeanUtilExt.copyProperties(userResult, user);
        } else {
            throw new BusinessException("9997", "用户不存在");
        }
        return userResult;
    }

    @Override
    @Transactional
    public UserResult register(String nickname, String phonenumber, String email, String password, String ip) throws
            BusinessException {
        User user = userDao.findUserByNicknameForupdate(nickname);
        if(user != null) {
            throw new BusinessException("9993", "nickname已存在");
        }
        User user1 = new User();
        user1.setPassword(password);
        user1.setCreateTime(new Date());
        user1.setUpdateTime(new Date());
        user1.setBindPhone(phonenumber);
        user1.setEmail(email);
        user1.setHeadImage("");
        user1.setIsNotfiySign("N");
        user1.setLastLoginIp(ip);
        user1.setLastLoginTime(new Date());
        user1.setNickname(nickname);
        user1.setStatus("active");
        userDao.insert(user1);
        UserResult userResult = new UserResult();
        BeanUtilExt.copyProperties(userResult, user1);
        return userResult;
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
