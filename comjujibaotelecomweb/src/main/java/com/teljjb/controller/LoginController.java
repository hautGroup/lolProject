package com.teljjb.controller;

import com.alibaba.fastjson.JSON;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.ErrorCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dezhonger on 2017/5/8.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = {"/register/post"})
    public BaseResponse<UserResult> registMobile(ModelMap modelMap, HttpServletRequest request,
                                                 HttpServletResponse response) {
        BaseResponse<UserResult> mapiResult = new BaseResponse<UserResult>();
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        if(StringUtils.isEmpty(mobile) ) {
            mapiResult.setCode("9997");
            mapiResult.setMessage("手机号不正确");
            return mapiResult;
        }
        if(StringUtils.isEmpty(password) ) {
            mapiResult.setCode("9997");
            mapiResult.setMessage("密码不正确");
            return mapiResult;
        }
        if(StringUtils.isEmpty(email) ) {
            mapiResult.setCode("9997");
            mapiResult.setMessage("电子邮箱不正确");
            return mapiResult;
        }
        if(StringUtils.isEmpty(nickname) ) {
            mapiResult.setCode("9997");
            mapiResult.setMessage("昵称不正确");
            return mapiResult;
        }
        try {
            UserResult result = userService.register(nickname, mobile, email, password);
            mapiResult.setResult(result);
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[LoginController.registMobile],params : " + mobile+","+password+",smsCode" , e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;
    }
}
