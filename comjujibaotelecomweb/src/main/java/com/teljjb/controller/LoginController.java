package com.teljjb.controller;

import com.teljjb.entity.Constant;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserLoginLogService;
import com.teljjb.service.api.UserService;
import com.teljjb.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Created by dezhonger on 2017/5/8.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginLogService userLoginLogService;


    /**
     * 注册
     *
     * @param modelMap
     * @param request
     * @param response
     * @return 用户信息
     */
    @ResponseBody
    @RequestMapping(value = {"/register/post"})
    public BaseResponse<UserResult> registMobile(ModelMap modelMap, HttpServletRequest request,
                                                 HttpServletResponse response) {
        BaseResponse<UserResult> mapiResult = new BaseResponse<>();
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");

        if (userService.findUserResultByEmail(email) != null) {
            mapiResult.setCode(-9995);
            mapiResult.setMessage("该电子邮箱已经被注册");
            return mapiResult;
        }


        try {
            UserResult result = userService.register(nickname, mobile, email, password, IpUtil.getIp(request));
            mapiResult.setResult(result);
            JavaMailUtil.sendForActive(email, result);
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[LoginController.registMobile],params : " + mobile + "," + password + ",smsCode", e);
            if(e.getMessage().equals("Invalid Addresses")) {
                mapiResult.setCode(-9992);
                mapiResult.setMessage(e.getMessage());
            } else {
                mapiResult.setCode(ErrorCode.UNKONE_ERROR);
                mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
            }


        }
        return mapiResult;
    }

    /**
     * 邮箱验证
     *
     * @param request
     * @param response
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/validateEmail")
    public BaseResponse<String> validateEmail(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        BaseResponse<String> mapiResult = new BaseResponse<>();
        if (StringUtils.isEmpty(email)) {
            mapiResult.setCode(-1);
            mapiResult.setMessage("邮箱地址不能为空");
            return mapiResult;
        }
        if (!EmailRegexUtil.checkEmaile(email)) {
            mapiResult.setCode(-2);
            mapiResult.setMessage("邮箱地址格式不正确");
            return mapiResult;
        }

        UserResult user = userService.findUserResultByEmail(email);
        if (user != null) {
            mapiResult.setCode(-3);
            mapiResult.setMessage("邮箱地址已经被注册");
            return mapiResult;
        }

        return mapiResult;
    }

    /**
     * 邮箱激活账号
     *
     * @param request
     * @param response
     * @return 激活成功的用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/active/account")
    public BaseResponse<UserResult> activeAccount(HttpServletRequest request, HttpServletResponse response) {
        String nickname = request.getParameter("nickname");
        String code = request.getParameter("code");
        UserResult userResult = userService.findUserResultByNickname(nickname);
        BaseResponse<UserResult> mapiResult = new BaseResponse<>();
        if (userResult == null) {
            mapiResult.setCode(-1);
            mapiResult.setMessage("无效的链接");
        } else {
            String code2 = MD5Util.getMD5Str("lol" + nickname + userResult.getId() + "lol");
            if (!code2.equals(code)) {
                mapiResult.setCode(-1);
                mapiResult.setMessage("激活失败");
            } else {
                if (userService.activeAccount(userResult.getId(), "active") > 0) {
                    mapiResult.setCode(1);
                    mapiResult.setMessage("激活成功");
                    userResult.setStatus("active");
                } else {
                    mapiResult.setCode(-1);
                    mapiResult.setMessage("您的账号已经激活过了。");
                }
            }
        }
        mapiResult.setResult(userResult);
        return mapiResult;
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<UserResult> login(HttpServletRequest request, HttpServletResponse response) {
        BaseResponse<UserResult> mapiResult = new BaseResponse<>();
        UserResult userResult = new UserResult();
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String platform = request.getParameter("platform");
        String deviceNumber = request.getParameter("deviceNumber");
        try {
            userResult = userService.userLogin(nickname, MD5Util.getMD5Str(Constant.PREMD5 + password));
            userLoginLogService.userLoginLog(userResult.getId(), platform, deviceNumber, IpUtil.getIp(request));
            mapiResult.setResult(userResult);
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[LoginController.login],params : ", e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;
    }


    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/upHeadImage")
    // , method = { RequestMethod.POST }
    public BaseResponse uploadHeadImage(@RequestParam("uploadFile") MultipartFile uploadFile,
                                        ModelMap modelMap, HttpServletRequest request,
                                        HttpServletResponse response) {
        BaseResponse mapiResult = new BaseResponse();
        try {
            Integer userId = this.getCurrUserId(request);
            LOG.info("上传头像,userId=" + userId + ",contentType=" + uploadFile.getContentType()
                    + ",filename=" + uploadFile.getOriginalFilename());
            // String realFileName = uploadFile.getOriginalFilename();
            InputStream file = uploadFile.getInputStream();
            // String headImageUrl = uploadOSSFile("headImage/", fileName,
            // uploadFile);
            String headImageUrl = shopImageHandler.uploadImage(file, true, request, 90, 90,
                    "headImage");
            Boolean status = userService.uploadHeadImage(headImageUrl, userId);
            if (status == true) {
                mapiResult.setCode(0);
                mapiResult.setResult(headImageUrl);
            } else {
                mapiResult.setCode(1);
                mapiResult.setMessage("更新头像失败");
            }
            return mapiResult;
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[LoginController.uploadHeadImage]", e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;

    }


}
