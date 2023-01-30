package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingGroup { //用户关注分组表

    private Long id;

    private Long userId; //用户id

    private String name; //关注分组的名称

    private String type; //关注分组类型：0特别关注  1悄悄关注 2默认分组  3用户自定义分组

    private Date createTime; //创建时间

    private Date updateTime; //更新时间

    private List<UserInfo> followingUserInfoList; //关注的用户的信息
}
