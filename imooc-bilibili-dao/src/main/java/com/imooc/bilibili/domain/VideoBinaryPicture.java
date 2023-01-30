package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoBinaryPicture { //视频二值图记录

    private Long id; //主键id

    private Long videoId; //视频id

    private Integer frameNo; //帧数

    private String url; //图片链接

    private Long videoTimestamp; //视频时间戳

    private Date createTime; //记录创建时间

}
