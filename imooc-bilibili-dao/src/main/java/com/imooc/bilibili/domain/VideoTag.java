package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoTag { //视频标签关联表

    private Long id; //主键id

    private Long videoId; //视频id

    private Long tagId; //标签id

    private Date createTime; //创建时间

}
