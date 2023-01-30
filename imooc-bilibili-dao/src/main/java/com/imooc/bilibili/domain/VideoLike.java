package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoLike { //视频点赞记录表

    private Long id; //主键id

    private Long userId; //用户id

    private Long videoId; //视频投稿id

    private Date createTime; //创建时间

}
