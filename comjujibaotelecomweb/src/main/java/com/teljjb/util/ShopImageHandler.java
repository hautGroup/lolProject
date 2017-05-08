package com.teljjb.util;

import net.coobird.thumbnailator.Thumbnailator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ShopImageHandler {
	
	private Logger logger = Logger.getLogger(ShopImageHandler.class);
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyymmdd");
	private ExecutorService executorService = Executors.newFixedThreadPool(30);
	
	public String uploadImage(InputStream in, boolean isThum, HttpServletRequest request, Integer width, Integer height, String dir) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
				String dest = UUIDUtil.getUUID()+".jpg";
				if(isThum){
					if(width==null){
						width=Integer.valueOf(PropertiesHelp.getProperty("IMG_THUMB_WIDTH"));
						height=width;
					}
					Thumbnailator.createThumbnail(in, os, 
							width, height);
					in = new ByteArrayInputStream(os.toByteArray());
				}
				String logoRealPathDir = PropertiesHelp.getProperty("IMG_SHARE_PATH");
				/** 根据真实路径创建目录 **/
				String path = "";
				if(dir!=null && !StringUtil.isEmpty(dir))
				{
					if(dir.equals("headImage"))
					{
						path = dir + "/" + DATE_FORMAT.format(new Date()) + "/";
						logoRealPathDir = logoRealPathDir + "/" + path;
					}
				}
				File file = new File(logoRealPathDir);
				if (!file.exists())
					file.mkdirs();
				File fileName = new File(logoRealPathDir+"/"+dest);
				
				inputstreamtofile(in,fileName);
				if(!fileName.exists()){
					fileName.createNewFile();
				}
				dest=PropertiesHelp.getProperty("SHARE_STATIC_HOST")+path+dest;
				return dest;
		} catch(IOException e1) {
			logger.error("Upload image error.", e1);
			throw new Exception("上传失败",e1);
			
			
		} catch(Exception e2) {
			logger.error("Upload image error.", e2);
			throw new Exception("上传失败",e2);
			
		}finally{
			if(in!=null){
				try {
					in.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	public void inputstreamtofile(InputStream ins,File file) throws Exception{
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		      os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
	}
	
	public String uploadUserImage(String headImage) throws Exception {
		String destFilename= UUIDUtil.getUUID()+".jpg";
		String date=DateUtil.getNowDateStr().replaceAll("-", "");
		executorService.execute(new AsncUploadUserHeadImage(headImage,date,destFilename));
		return PropertiesHelp.getProperty("IMG_SERVER_HOST")+date+"/"+destFilename;
	}
	

	class AsncUploadUserHeadImage extends Thread{
		private String headImage;
		private String date;
		private String destFilename;
		public AsncUploadUserHeadImage(String headImage,String date,String destFilename){
			this.headImage = headImage;
			this.date=date;
			this.destFilename = destFilename;
		}
		public void run(){
			InputStream is=null;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try{
					int width = 0;
					int height = 0;
					width=Integer.valueOf(PropertiesHelp.getProperty("IMG_THUMB_WIDTH"));
					height=width;
					is=HttPClientUtil.doGetInputStream(headImage);
					Thumbnailator.createThumbnail(is, os, width, height);
					is = new ByteArrayInputStream(os.toByteArray());
					String logoRealPathDir = PropertiesHelp.getProperty("IMG_SERVER_THUMB_PATH")+"/"+date;
					/** 根据真实路径创建目录 **/
					File file = new File(logoRealPathDir);
					if (!file.exists())
						file.mkdirs();
					
					File fileName = new File(logoRealPathDir+"/"+destFilename);
					inputstreamtofile(is,fileName);
					if(!fileName.exists()){
						fileName.createNewFile();
					}
			} catch(IOException e1) {
				logger.error("Upload image error.", e1);
				try {
					throw new Exception("上传失败",e1);
				} catch (Exception e) {
				}
				
				
			} catch(Exception e2) {
				logger.error("Upload image error.", e2);
				try {
					throw new Exception("上传失败",e2);
				} catch (Exception e) {
				}
				
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	public static void main(String[] args) throws Exception {
		ShopImageHandler hean = new ShopImageHandler();
		String url=hean.uploadUserImage("http://wx.qlogo.cn/mmopen/RXbZdotBvPjK9uMHKanwSsmyJEtV1x8MS6bzUmMqGqjnUlibRekq8FE3wJQWHaSeRrMx2PDwTAdwxiaCaicmcnCbibhMgrvCutWo/0");
		System.out.println(url);
	}

}
