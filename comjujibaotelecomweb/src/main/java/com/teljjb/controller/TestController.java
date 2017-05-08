package com.teljjb.controller;

import com.teljjb.response.BaseResponse;
import com.teljjb.util.IpUtil;
import com.teljjb.util.JavaMailUtil;
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
public class TestController extends BaseController {

    @ResponseBody
    @RequestMapping(value = {"/ip"})
    public BaseResponse<String> ip(ModelMap modelMap, HttpServletRequest request,
                                   HttpServletResponse response) {
        BaseResponse<String> re = new BaseResponse<>();
        String ip = IpUtil.getIp(request);
        LOG.info("ip = " + ip);
        re.setResult(ip);
        return re;
    }


    @ResponseBody
    @RequestMapping(value = {"/sendmail"})
    public BaseResponse<String> sendmail(ModelMap modelMap, HttpServletRequest request,
                                   HttpServletResponse response) {
        BaseResponse<String> re = new BaseResponse<>();
        String email = request.getParameter("mail");
        LOG.info("email = " + email);
        try {
            JavaMailUtil.sendMail(email);
            re.setResult("success");
        } catch (Exception e) {
            re.setResult("fail");
            re.setMessage(e.getMessage());
        }
        return re;
    }




}