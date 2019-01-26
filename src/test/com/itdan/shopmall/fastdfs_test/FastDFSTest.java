package com.itdan.shopmall.fastdfs_test;

import com.itdan.shopmall.BaseTest;
import com.itdan.shopmall.utils.common.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * FastDFS图片服务器测试
 */
public class FastDFSTest extends BaseTest {

    @Test
     public void testUploadImg () throws Exception{

        //创建一个配置文件，文件名可以任意，里面用来存储tracker服务器的地址
        //加载配置文件
        ClientGlobal.init("D:/idea/wokespace/shopmall/src/main/resources/client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient对象获取一个TrackerService对象
        TrackerServer trackerServer=trackerClient.getConnection();
        //创建一个StorageServer引用,可以为null
        StorageServer storageServer=null;
        //创建一个StorageClient,需要以上的两个参数
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        //在上传文件
        String[]upload_file=storageClient.upload_appender_file(
                "C:/Users/WIN8/Pictures/Camera Roll/37747332a8e0d6042a4e55846e053350.png",
                "png",
                null);
        //返回数组。包含组名和图片的路径。
        for (String string : upload_file) {
            System.out.println(string);
        }
    }

    @Test
    public void testUploadImg1() throws Exception{
     //测试封装后的FastDFSClient工具
        FastDFSClient fastDFSClient=new FastDFSClient("D:/idea/wokespace/shopmall/src/main/resources/client.conf");
        String fileName = fastDFSClient.uploadFile("C:/Users/WIN8/Pictures/Camera Roll/37747332a8e0d6042a4e55846e053350.png");
        System.out.println(fileName);
    }
}
