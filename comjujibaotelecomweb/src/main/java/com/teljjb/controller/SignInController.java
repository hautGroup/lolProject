package com.teljjb.controller;

import com.teljjb.entity.SiteContextThreadLocal;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.UserSignInRecordResult;
import com.teljjb.service.api.SignInService;
import com.teljjb.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @ResponseBody
    @RequestMapping(value = { "play" })
    public BaseResponse<UserSignInRecordResult> playTask(HttpServletRequest request,
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
            LOG.error("系统出错[TaskController.playTask],params="
                            + SiteContextThreadLocal.get().getRequestBody(),
                    e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;
    }


}
