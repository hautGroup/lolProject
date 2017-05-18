package com.teljjb.controller;

import com.teljjb.entity.Constant;
import com.teljjb.entity.ServiceContextThreadLocal;
import com.teljjb.exception.BusinessException;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.MD5Util;
import com.teljjb.util.ShopImageHandler;
import com.teljjb.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dezhonger on 2017/5/15.
 */
public class AppBaseController {

    public static final Logger LOG			= Logger.getLogger(AppBaseController.class);

    @Autowired
    protected ShopImageHandler shopImageHandler;

    @Autowired
    private UserService userService;

    public static final String		CHECK_CODE	= "checkCode";
    public static final String		UNIQUE_ID	= "unique_id";
    public static final String		SESSION_ID	= "session_id";
    public static final String		COUNTWRONG	= "count_wrong";

    protected static final String	EXPJSON		= "application/x-json";

    public Integer getCurrentUserId(HttpServletRequest request) throws BusinessException {

//        String userSessionId = ServiceContextThreadLocal.get().getUserSessionId();
        String userId = ServiceContextThreadLocal.get().getUserId();
        String platform = ServiceContextThreadLocal.get().getPlatform();
        String password = ServiceContextThreadLocal.get().getPassword();
        LOG.info("userId:" + userId + ",userSessionId:" + password);
        if (StringUtil.isEmpty(password) || StringUtil.isEmpty(userId)) {
            throw new BusinessException(-9999, "请重新登录！");
        }
        UserResult user = userService.findUserByIdAndPass(Integer.parseInt(userId), MD5Util.getMD5Str(Constant.PREMD5 + password));
        if(user.getStatus().equals("lock")) {
            throw new BusinessException(-9999, "请激活您的用户！");
        }
        return user.getId();

    }

}
