package com.teljjb.controller;

import com.teljjb.bean.PlanSchedule;
import com.teljjb.entity.ServiceContextThreadLocal;
import com.teljjb.entity.SiteContextThreadLocal;
import com.teljjb.exception.BusinessException;
import com.teljjb.response.BaseResponse;
import com.teljjb.result.PlanResult;
import com.teljjb.result.UserSignInRecordResult;
import com.teljjb.service.api.PlanScheduleService;
import com.teljjb.service.api.PlanService;
import com.teljjb.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.dc.pr.PRError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dezhonger on 2017/5/18.
 */
@Controller
@RequestMapping(value = {"/plan"})
public class PlanController extends AppBaseController {


    @Autowired
    private PlanService planService;

    @Autowired
    private PlanScheduleService planScheduleService;


    /**
     * 用户添加计划
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "add" })
    public BaseResponse<PlanResult> addplan(HttpServletRequest request,
                                            HttpServletResponse response) {
        BaseResponse<PlanResult> mapiResult = new BaseResponse<>();

        try {
            Integer userId = getCurrentUserId(request);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endTime = sdf.parse(request.getParameter("endTime"));
            String topic = request.getParameter("topic");
            String synopsis = request.getParameter("synopsis");
            String content = request.getParameter("content");
            PlanResult result = planService.addPlain(userId, topic, synopsis, content, endTime);
            mapiResult.setResult(result);
        } catch (BusinessException e) {
            mapiResult.setCode(e.getCode());
            mapiResult.setMessage(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            LOG.error("系统出错[PlanController.addplan],params="
                            + ServiceContextThreadLocal.get().getRequestBody(),
                    e);
            mapiResult.setCode(ErrorCode.UNKONE_ERROR);
            mapiResult.setMessage(ErrorCode.UNKONE_ERROR_MSG);
        }
        return mapiResult;
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date endTime = sdf.parse("2017-05-18 00:00:00");
            System.out.println(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
