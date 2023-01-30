package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionGroup { //收藏分组表

    private Long id;

    private Long userId;

    private String name; //关注分组名称

    private String type; //关注分组类型：0默认分组  1用户自定义分组

    private Date createTime;

    private Date updateTime;

}
