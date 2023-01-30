package com.imooc.bilibili.dao;

import com.imooc.bilibili.domain.BulletChat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BulletChatDao {

    Integer addBulletChat(BulletChat bulletChat);

    List<BulletChat> getBulletChats(Map<String, Object> params);
}
