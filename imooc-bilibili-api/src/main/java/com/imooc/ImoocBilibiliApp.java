package com.imooc;

import com.imooc.bilibili.service.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启事务支持
@EnableAsync //启用Spring的异步方法执行功能
@EnableScheduling //开启对定时任务的支持
public class ImoocBilibiliApp {

    /**
     * 进入redis安装根目录，执行：redis-server.exe
     *
     * D:\rocketmq-all-5.0.0\bin
     * 进入mq的bin目录，启动：mqnamesrv.cmd（名称服务器）
     * 进入mq的bin目录，启动：mqbroker.cmd -n localhost:9876 autoCreateTopicEnable=true（代理服务器）
     *
     * FastDFS
     * 启动 tracker：fdfs_trackerd /etc/fdfs/tracker.conf
     * 启动 storage：fdfs_storaged /etc/fdfs/storage.conf
     * 启动 nginx：/usr/local/nginx/sbin/nginx
     * http://192.168.52.128:8888/
     *
     * 访问前端在线调试地址
     * 	1、前端在线调试地址：http://39.107.54.180/html/imooc-bilibili
     * 	2、进入前端页面后，首先填写自己本地后端应用公网映射后的地址
     * 	3、选择对应章节可以调试的模块内容进行效果查看
     */

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(ImoocBilibiliApp.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
