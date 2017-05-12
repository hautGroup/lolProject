package com.teljjb.controller;

import com.teljjb.entity.SiteContextThreadLocal;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.UserResult;
import com.teljjb.result.UserSignInRecordResult;
import com.teljjb.service.api.SignInService;
import com.teljjb.service.api.UserService;
import com.teljjb.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dezhonger on 2017/5/11.
 */
@Controller
@RequestMapping(value = {"/signin"})
public class SignInController extends BaseController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private UserService userService;

    /**
     * 用户签到
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "play" })
    public BaseResponse<UserSignInRecordResult> playSignin(HttpServletRequest request,
                                                         HttpServletResponse response) {
        BaseResponse<UserSignInRecordResult> mapiResult = new BaseResponse<>();
        UserSignInRecordResult res = new UserSignInRecordResult();
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        try {
            res = signInService.playSignIn(userId);
            mapiResult.setResult(res);
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[SignInController.playSignin],params="
                            + SiteContextThreadLocal.get().getRequestBody(),
                    e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;
    }

    @ResponseBody
    @RequestMapping(value = { "change/status" })
    public BaseResponse<Boolean> changeStatus(HttpServletRequest request, HttpServletResponse response) {
        BaseResponse<Boolean> mapiResult = new BaseResponse<>();
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        String status = request.getParameter("status");
        try {
            mapiResult.setResult(userService.changeSigninStatus(userId, status));
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[SignInController.changeStatus],params="
                            + SiteContextThreadLocal.get().getRequestBody(),
                    e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;
    }


}
