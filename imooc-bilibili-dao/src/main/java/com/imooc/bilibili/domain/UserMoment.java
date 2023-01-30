package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMoment { //用户动态表

    private Long id; //主键id

    private Long userId; //用户id

    private String type; //动态类型：0视频 1直播 2专栏动态

    private Long contentId; //内容详情id

    private Date createTime; //创建时间

    private Date updateTime; //更新时间
}
