package com.taotao.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

public class TestFastDFS {

	@Test
	public void testFastDFS() throws FileNotFoundException, IOException, MyException {
		//1 引入FastDFS的jar
		//2 创建一个配置文件   指定tracker服务器的地址   tracker服务器相当于是一个注册中心   中间件
		//3 加载配置文件	 
		ClientGlobal.init("C:\\Java\\eclipse\\workspace\\taotao-manager-web\\src\\main\\resources\\fastdfs\\client.conf");
		//4 创建一个TrackerClient对象
		TrackerClient client = new TrackerClient();
		//5 使用trackerClient对象获得一个trackerServer对象
		TrackerServer connection = client.getConnection();
		//6 创建一个StorageServer的ref null就可以
		StorageServer server = null;
		//7 创建一个StorageClient对象  trackerServer、StrongServer两个参数
		StorageClient storageClient = new StorageClient(connection, server);
		//8 使用StrongClient上传文件
		String[] upload_file = storageClient.upload_file("C:\\Users\\fx\\Pictures\\2aeb7c3c1aa61f5b.jpg", "jpg", null);
		for (String s : upload_file) {
			System.out.println(s);
		}
	}
	
	@Test
	public void test2() throws Exception {
		FastDFSClient client= new FastDFSClient("C:\\Java\\eclipse\\workspace\\taotao-manager-web\\src\\main\\resources\\fastdfs\\client.conf");
		String uploadFile = client.uploadFile("C:\\Users\\fx\\Pictures\\2aeb7c3c1aa61f5b.jpg");
		System.out.println(uploadFile);
	
	}
	
}