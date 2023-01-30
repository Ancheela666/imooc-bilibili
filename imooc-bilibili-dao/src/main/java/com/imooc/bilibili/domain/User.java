package com.imooc.bilibili.domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User { //用户表

    private Long id; //用户id

    private String phone; //手机号

    private String email; //邮箱

    private String password; //密码

    private String salt; //盐值（加密用）

    private Date createTime; //创建时间

    private Date updateTime; //更新时间

    private UserInfo userInfo; //用户对应的基本信息
}
