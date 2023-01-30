package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreference { //用户偏好信息

    private Long id;

    private Long userId;

    private Long videoId;

    private Float value; //用户行为得分

    private Date createTime;

}
