package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowing { //用户关注信息表

    private Long id;

    private Long userId; //用户id

    private Long followingId; //关注用户的id

    private Long groupId; //关注分组id

    private Date createTime; //创建时间

    private UserInfo userInfo;
}
