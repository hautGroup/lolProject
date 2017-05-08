package com.teljjb.controller;

import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.EmailRegexUtil;
import com.teljjb.util.ErrorCode;
import com.teljjb.util.IpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @ResponseBody
    @RequestMapping(value = {"/register/post"})
    public BaseResponse<UserResult> registMobile(ModelMap modelMap, HttpServletRequest request,
                                                 HttpServletResponse response) {
        BaseResponse<UserResult> mapiResult = new BaseResponse<>();
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        if(StringUtils.isEmpty(mobile) ) {
            mapiResult.setCode(-9997);
            mapiResult.setMessage("手机号不正确");
            return mapiResult;
        }
        if(StringUtils.isEmpty(password) ) {
            mapiResult.setCode(-9996);
            mapiResult.setMessage("密码不正确");
            return mapiResult;
        }
        if(StringUtils.isEmpty(email) || !EmailRegexUtil.checkEmaile(email) || userService.findUserResultByEmail(email) != null) {
            mapiResult.setCode(-9995);
            mapiResult.setMessage("电子邮箱不正确");
            return mapiResult;
        }
        if(StringUtils.isEmpty(nickname) ) {
            mapiResult.setCode(-9994);
            mapiResult.setMessage("昵称不正确");
            return mapiResult;
        }
        try {
            UserResult result = userService.register(nickname, mobile, email, password, IpUtil.getIp(request));
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

    @ResponseBody
    @RequestMapping(value = "/validateEmail")
    public BaseResponse<String> validateEmail(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        BaseResponse<String> mapiResult = new BaseResponse<>();
        if(StringUtils.isEmpty(email) ) {
            mapiResult.setCode(-1);
            mapiResult.setMessage("邮箱地址不能为空");
            return mapiResult;
        }
        if(!EmailRegexUtil.checkEmaile(email)) {
            mapiResult.setCode(-2);
            mapiResult.setMessage("邮箱地址格式不正确");
            return mapiResult;
        }

        UserResult user = userService.findUserResultByEmail(email);
        if(user != null) {
            mapiResult.setCode(-3);
            mapiResult.setMessage("邮箱地址已经被注册");
            return mapiResult;
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
