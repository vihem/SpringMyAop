package com.easue.test;

import com.easue.config.AppConfig;
import com.easue.dao.IndexDao;
import org.csource.fastdfs.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean(IndexDao.class).query();
        context.getBean(IndexDao.class).query2();
    }
    @org.junit.Test
    public void uploadFile() throws Exception {
        /*
         * 1. �򹤳��м�jar��
         * 		���maven���̣�fastdfs_client
         * 		run as maven build
         * 		���pom
         * 2. ���������ļ�������tracker��������ַ
         * 		/taotao-manager-web/src/main/resources/resource/client.conf
         * 		tracker_server=192.168.25.133:22122
         * 3. ���������ļ�
         * 4. ����һ��TrackerClient����
         * 5. ʹ��TrackerClient������trackerserver����
         * 6. ����StorageServer�����ã�null�Ϳ�����
         * 7. ����StorageClient����trackerserver��StorageServer��������
         * 8. ʹ��StorageClient�����ϴ��ļ�
         */
//        //3.���������ļ�
        ClientGlobal.init("D:/work-eclipse-tt/taotao-manager-web/src/main/resources/resource/client.conf");
        //4��newһ��TrackerClient����
        TrackerClient trackerClient = new TrackerClient();
        //5����ȡtrackerserver����
        TrackerServer trackerServer = trackerClient.getConnection();
        //6������һ��StorageServer����
        StorageServer storageServer = null;
        //7��newһ��StorageClient����
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //8.ʹ��StorageClient�����ϴ��ļ�
        String[] strings = storageClient.upload_file("D:/1.jpg","jpg" , null);//������null�Ǳ�ע��Ϣ
        for (String string : strings) {
            System.out.println(string);
        }
    }

}
