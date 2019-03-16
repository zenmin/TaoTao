package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.utils.FastDFSClient;
import com.taotao.utils.JsonUtils;


/**
 * @Title ImageController
 * @Description 本类主要功能是图片上传controller
 * @Company null
 * @author 曾敏
 * @date 2017年9月4日下午4:45:56
 * 
 * 返回格式(JSON)
		//成功时
		{
		        "error" : 0,
		        "url" : "http://www.example.com/path/to/file.ext"
		}
		//失败时
		{
		        "error" : 1,
		        "message" : "错误信息"
		}
 */
@SuppressWarnings("rawtypes")
@Controller
public class ImageController {

	//取配置文件中的属性  前提是要在spring容器加载的时候读取配置文件
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL; 

	@SuppressWarnings("unchecked")
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uplaodImg(MultipartFile uploadFile)  {

		try {
			//接收上传的图片
			//获取文件扩展名
			String filename = uploadFile.getOriginalFilename();		//取文件名
			String extName = filename.substring(filename.lastIndexOf(".") + 1);		//取扩展名

			//上传到FastDFS服务器src/main/resources
			FastDFSClient client = new FastDFSClient("src/main/resources/fastdfs/client.conf");
			String url = client.uploadFile(uploadFile.getBytes(), extName);		//获取到上传的文件转为字节  并且加上扩展名
			//加上图片服务器的地址
			String imageUrl = IMAGE_SERVER_URL + url;
			//响应上传图片的url
		
			Map map = new HashMap<>();
			map.put("error", 0);		//成功
			map.put("url", imageUrl);	//响应图片地址给前端
			return JsonUtils.objectToJson(map);		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Map map = new HashMap<>();	
			map.put("error", 1);		//失败
			map.put("message", "上传失败");
			return JsonUtils.objectToJson(map);
		}

	}
}
