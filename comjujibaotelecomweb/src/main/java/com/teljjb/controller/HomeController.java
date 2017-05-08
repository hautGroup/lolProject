package com.teljjb.controller;

import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.UserResult;
import com.teljjb.service.api.UserService;
import com.teljjb.util.ErrorCode;
import com.teljjb.util.PropertiesHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dezhonger on 2017/4/24.
 */

@Controller
public class HomeController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/index"})
    public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        modelMap.put("html1", "111");
        modelMap.put("html2", "222");
        modelMap.put("html3", "333");
        modelMap.put("html4", "444");
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = {"/heart"})
    public String heart(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        return "heart";
    }

    @ResponseBody
    @RequestMapping(value = {"/lol"})
    public BaseResponse<List<UserResult>> lol(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        BaseResponse<List<UserResult>> result = new BaseResponse<List<UserResult>>();
        UserResult user1 = new UserResult();
        user1.setId(1);
        user1.setNickname("dezhonger1");
        user1.setNickname("zhangweilong1");
        UserResult user2 = new UserResult();
        user2.setId(2);
        user2.setNickname("dezhonger2");
        user2.setNickname("zhangweilong2");
        UserResult user3 = new UserResult();
        user3.setId(3);
        user3.setNickname("dezhonger3");
        user3.setNickname("zhangweilong3");
        List<UserResult> list = new ArrayList<UserResult>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        result.setResult(list);
        return result;
    }

    @RequestMapping(value = {"/lol2"})
    public String wuziqi(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        String domain = PropertiesHelp.getProperty("projectDomain");
        modelMap.put("domain", domain);
        return "/1";
    }

    @ResponseBody
    @RequestMapping(value = {"/horseracelamp"})
    public BaseResponse<UserResult> horseracelamp(HttpServletRequest request, HttpServletResponse
            response, @RequestParam(required = false, defaultValue = "1") int id) {
        BaseResponse<UserResult> mapiResult = new BaseResponse<UserResult>();
        UserResult userResult = null;
        try {
            userResult = userService.findUserResultById(id);
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("系统出错[HomeController.horseracelamp]," , e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        mapiResult.setResult(userResult);
        return mapiResult;
    }


}
