package com.teljjb.controller;

import com.teljjb.entity.SiteContextThreadLocal;
import com.teljjb.util.ShopImageHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dezhonger on 2017/4/24.
 */
public class BaseController {

    public static final Logger LOG = Logger.getLogger(BaseController.class);

    @Autowired
    protected ShopImageHandler shopImageHandler;

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
                              HttpServletResponse response, String result) {
        PrintWriter out;
        try {
            out = response.getWriter();
            String callback = request.getParameter("callback");

            LOG.info("callback:" + callback);

            String jsoncallback = "";
            if (StringUtils.isEmpty(callback)) {
                jsoncallback = result;
            } else {
                jsoncallback = callback + "(" + result + ")";
            }
            out.print(jsoncallback);
            out.flush();
            out.close();
        } catch (IOException e) {
            LOG.error("BaseController.asynOutResult error: ", e);
        }

    }

    public List<String> uploadMutiFile(HttpServletRequest request) throws Exception {
        List<String> imgUrls = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    InputStream in = file.getInputStream();
                    String imgUrl = shopImageHandler.uploadImage(in, true, request, null, null, null);
                    imgUrls.add(imgUrl);
                }
                //记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                LOG.info("uploadMutiFile times : " + (finaltime - pre));
            }

        }
        return imgUrls;
    }


    public Integer getCurrUserId(HttpServletRequest request) {
        String userId = SiteContextThreadLocal.get().getPassport().getPairMap().get("id");

        return userId == null ? null : new Integer(userId);
    }
}
