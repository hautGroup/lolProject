package com.teljjb.controller;

import com.teljjb.entity.ServiceContextThreadLocal;
import com.teljjb.exception.BusinessException;
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

    public static final String		CHECK_CODE	= "checkCode";
    public static final String		UNIQUE_ID	= "unique_id";
    public static final String		SESSION_ID	= "session_id";
    public static final String		COUNTWRONG	= "count_wrong";

    protected static final String	EXPJSON		= "application/x-json";

    public Integer getCurrentUserId(HttpServletRequest request) throws BusinessException {

        String userSessionId = ServiceContextThreadLocal.get().getUserSessionId();
        String userId = ServiceContextThreadLocal.get().getUserId();
        String platform = ServiceContextThreadLocal.get().getPlatform();
        LOG.info("userId:" + userId + ",userSessionId:" + userSessionId);
        if (StringUtil.isEmpty(userSessionId) || StringUtil.isEmpty(userId)) {
//            throw new BusinessException(ErrorConstants.USER_LOGIN_AGAIN[0],
//                    ErrorConstants.USER_LOGIN_AGAIN[1]);
            throw new BusinessException(-9999, "请重新登录！");
        }
        //查询用户ID

//        JjbBasicLoginUser loginUser=basicUserService.checkUserSessionForApp(Integer.parseInt(userId), userSessionId,platform);
//
//        LOG.info("userId:" + userId + ",userSessionId:" + userSessionId + "isLogin:" + loginUser.getIsLogin());
//        if (loginUser.getIsLogin() == false) {
//            throw new BusinessException(ErrorConstants.USER_LOGIN_AGAIN[0],
//                    ErrorConstants.USER_LOGIN_AGAIN[1]);
        if(false) {
            throw new BusinessException(-9999, "请重新登录！");
        }
//        return Integer.parseInt(userId);
        return 123456;

    }

}
