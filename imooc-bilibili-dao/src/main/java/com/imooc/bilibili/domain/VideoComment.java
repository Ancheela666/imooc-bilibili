package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoComment { //视频评论表

    private Long id; //主键id

    private Long videoId; //视频id

    private Long userId; //用户id

    private String comment; //评论

    private Long replyUserId; //回复用户id

    private Long rootId; //根节点评论（一级评论）id

    private Date createTime; //创建时间

    private Date updateTime; //更新时间

    private List<VideoComment> childList;

    private UserInfo userInfo; //用户信息

    private UserInfo replyUserInfo; //回复用户的信息

}
