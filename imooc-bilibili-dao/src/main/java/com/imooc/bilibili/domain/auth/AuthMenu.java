package com.imooc.bilibili.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthMenu {

    private Long id;

    private String name;

    private String code;

    private Date createTime;

    private Date updateTime;

}
