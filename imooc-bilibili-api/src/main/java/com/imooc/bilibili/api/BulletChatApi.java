package com.imooc.bilibili.api;

import com.imooc.bilibili.api.support.UserSupport;
import com.imooc.bilibili.domain.BulletChat;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.service.BulletChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class BulletChatApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private BulletChatService bulletChatService;

    @GetMapping("/danmus")
    public JsonResponse<List<BulletChat>> getDanmus(@RequestParam Long videoId, //必传的参数
                                                    String startTime,
                                                    String endTime) throws ParseException {
        List<BulletChat> list;
        try{ //判断当前是游客模式还是用户登录模式
            userSupport.getCurrentUserId();
            //若是用户登录模式，则允许用户进行时间段筛选
            list = bulletChatService.getBulletChats(videoId, startTime, endTime);
        } catch (Exception ignored){
            //若为游客模式，则不允许用户进行时间段筛选
            list = bulletChatService.getBulletChats(videoId, null, null);
        }
        return new JsonResponse<>(list);
    }

}
