package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletChat { //弹幕记录表

    private Long id; //主键id

    private Long userId; //用户id

    private Long videoId; //视频id

    private String content; //弹幕内容

    private String bulletTime; //弹幕在视频中出现的时间

    private Date createTime; //创建时间

}
