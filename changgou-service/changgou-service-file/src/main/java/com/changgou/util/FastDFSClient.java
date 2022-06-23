package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FastDFSClient {


    static {
        String path = "fdfs_client.conf";
        String config_name = new ClassPathResource(path).getPath();
        try {
            ClientGlobal.init(config_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] uploadFile(FastDFSFile fastDFSFile){

        try {
            // 获取文件相关属性
            byte[] file_buff = fastDFSFile.getContent();
            String ext_name = fastDFSFile.getExt();
            NameValuePair[] meta_list = new NameValuePair[1];
            meta_list[0] = new NameValuePair(fastDFSFile.getAuthor());
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、创建存储服务器客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 4、文件上传
            String[] uploadResult = storageClient.upload_file(file_buff, ext_name, meta_list);
            return uploadResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] downloadFile(String group_name, String remote_filename){
        try {
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、创建存储服务器客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 4、文件下载
            byte[] file_buff = storageClient.download_file(group_name, remote_filename);
            return file_buff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFile(String group_name, String remote_filename){
        try {
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、创建存储服务器客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 4、文件删除
            storageClient.delete_file(group_name, remote_filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileInfo getFileInfo(String group_name, String remote_filename){
        try {
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、创建存储服务器客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 4、获取文件信息
            FileInfo fileInfo = storageClient.get_file_info(group_name, remote_filename);
            return fileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取该组所在的存储服务器信息
     **/
    public static StorageServer getStorageServerInfo(String groupName){

        try {
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、创建存储服务器客户端
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer, groupName);
            return storeStorage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description 获取集群下的存储服务器信息
     **/
    public static ServerInfo[] getServerInfo(String groupName, String filename){

        try {
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、获取集群下的存储服务器信息
            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, filename);
            return serverInfos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTrackerUrl(){
        try {
            // 1、创建跟踪服务器客户端
            TrackerClient trackerClient = new TrackerClient();
            // 2、获取跟踪服务器
            TrackerServer trackerServer = trackerClient.getConnection();
            // 3、获取跟踪服务器地址
            String hostAddress = trackerServer.getInetSocketAddress().getAddress().getHostAddress();
            int port = ClientGlobal.getG_tracker_http_port();
            String url = "http://" + hostAddress + ":" + port;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
