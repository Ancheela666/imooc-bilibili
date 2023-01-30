package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCoin {

    private Long id; //主键id

    private Long videoId; //视频投稿id

    private Long userId; //用户id

    private Integer amount; //投币数

    private Date createTime;

    private Date updateTime;

}
