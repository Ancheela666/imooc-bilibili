package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private Long id;

    private String url;

    private String type;

    private String md5;

    private Date createTime;
}
