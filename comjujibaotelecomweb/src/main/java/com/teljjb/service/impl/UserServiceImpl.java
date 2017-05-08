package com.teljjb.service.impl;

import com.teljjb.bean.User;
import com.teljjb.dao.UserDao;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.BeanUtilExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dezhonger on 2017/5/8.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public UserResult findUserResultById(Integer id) {
        User user = userDao.findUserById(id);
        UserResult userResult = new UserResult();
        if(user != null) {
            BeanUtilExt.copyProperties(userResult, user);
        }
        return userResult;
    }
}
