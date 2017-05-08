package com.teljjb.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dezhonger on 2017/4/24.
 */
public class BaseController {

    public static final Logger LOG				= Logger.getLogger(BaseController.class);

    public static void outputStreamResult(HttpServletResponse response, String pageResultType,
                                          Object value, String uniqueId) {
        ServletOutputStream sos = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("image/jpg");
            // 将图像输出到Servlet输出流中。
            sos = response.getOutputStream();
            ImageIO.write((BufferedImage) value, "jpg", sos);
        } catch (Exception e) {
            LOG.error("output image failure uniqueId :" + uniqueId, e);
        } finally {
            if (sos != null)
                try {
                    sos.close();
                } catch (IOException e) {
                    LOG.info("输出验证码图片后无法关闭OutputStream" + ",  uniqueId :" + uniqueId, e);
                }
        }

    }

    protected void asynchronousPrintResult(HttpServletResponse response, String pageResultType,
                                           Object value) throws IOException {

        response.setContentType(pageResultType);
        response.setCharacterEncoding("UTF-8");

        // 以流的方式异步输了结果
        PrintWriter writer = response.getWriter();
        value = value == null ? "" : value;
        writer.print(value.toString());
        writer.flush();
        writer.close();

    }

    public void asynOutResult(HttpServletRequest request,
                              HttpServletResponse response,String result){
        PrintWriter out;
        try {
            out = response.getWriter();
            String  callback = request.getParameter("callback");

            LOG.info("callback:"+callback);

            String jsoncallback="";
            if(StringUtils.isEmpty(callback)){
                jsoncallback=result;
            }else{
                jsoncallback = callback + "("+result+")";
            }
            out.print(jsoncallback);
            out.flush();
            out.close();
        } catch (IOException e) {
            LOG.error("BaseController.asynOutResult error: ",e);
        }

    }
}
