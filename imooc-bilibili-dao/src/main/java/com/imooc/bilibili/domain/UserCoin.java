package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCoin { //用户硬币信息记录表

    private Long id; //主键id

    private Long userId; //用户id

    private Integer amount; //剩余硬币数

    private Date createTime; //记录创建时间

    private Date updateTime; //记录更新时间

}
