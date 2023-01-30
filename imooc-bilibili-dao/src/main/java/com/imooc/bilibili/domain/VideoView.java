package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoView { //视频观看记录

    private Long id; //主键id

    private Long videoId; //视频id

    private Long userId; //用户id

    private String clientId; //客户端id（操作系统+浏览器）

    private String ip; //IP地址

    private Date createTime; //记录创建时间

}
