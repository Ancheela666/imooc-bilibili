package com.imooc.bilibili.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.bilibili.dao.BulletChatDao;
import com.imooc.bilibili.domain.BulletChat;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BulletChatService {

    private static final String BULLET_KEY = "bullet-chat-video-";

    @Autowired
    private BulletChatDao bulletChatDao;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void addBulletChatToMySQL(BulletChat bulletChat){
        bulletChatDao.addBulletChat(bulletChat);
    }

    @Async
    public void asyncAddBulletChatToMySQL(BulletChat bulletChat){
        bulletChatDao.addBulletChat(bulletChat);
    }

    /** 查询弹幕数据：
     * 查询策略是优先查redis中的弹幕数据，
     * 如果没有的话查询数据库，然后把查询的数据写入redis当中
     */
    public List<BulletChat> getBulletChats(Long videoId, String startTime, String endTime) throws ParseException {
        String key = BULLET_KEY + videoId;
        String value = redisTemplate.opsForValue().get(key); //在redis中查询该视频对应的所有弹幕数据
        List<BulletChat> bulletChatList;
        if(!StringUtil.isNullOrEmpty(value)){ //redis中有相应的数据
            bulletChatList = JSONArray.parseArray(value, BulletChat.class);
            if(!StringUtil.isNullOrEmpty(startTime)
                    && !StringUtil.isNullOrEmpty(endTime)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);
                List<BulletChat> childList = new ArrayList<>();
                for (BulletChat bulletChat : bulletChatList) { //根据时间范围对弹幕进行筛选
                    Date createTime = bulletChat.getCreateTime();
                    if(createTime.after(startDate) && createTime.before(endDate)){ //确保返回的弹幕满足给定的时间范围
                        childList.add(bulletChat);
                    }
                }
                bulletChatList = childList; //将筛选后的数据赋给结果列表
            }
        } else{ //redis中没有相应的数据
            //从MySQL数据库中查询弹幕数据
            Map<String, Object> params = new HashMap<>();
            params.put("videoId", videoId);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            bulletChatList = bulletChatDao.getBulletChats(params);
            //保存弹幕数据到redis中
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(bulletChatList));
        }
        return bulletChatList;
    }

    public void addBulletChatsToRedis(BulletChat bulletChat){
        String key = BULLET_KEY + bulletChat.getVideoId();
        String value = redisTemplate.opsForValue().get(key);
        List<BulletChat> bulletChatList = new ArrayList<>();
        if(!StringUtil.isNullOrEmpty(value)){
            bulletChatList = JSONArray.parseArray(value, BulletChat.class);
        }
        bulletChatList.add(bulletChat);
        redisTemplate.opsForValue().set(key, JSONArray.toJSONString(bulletChatList));
    }

}
