package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCollection {

    private Long id;

    private Long videoId;

    private Long userId;

    private Long groupId; //收藏分组id

    private Date createTime;

}
