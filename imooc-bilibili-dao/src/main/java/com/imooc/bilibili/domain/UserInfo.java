package com.imooc.bilibili.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Getter
@Setter
@Document(indexName = "user-infos")
public class UserInfo { //用户基本信息表

    @Id
    private Long id;

    private Long userId; //用户id

    @Field(type = FieldType.Text)
    private String nick; //昵称

    private String avatar; //头像

    private String sign; //签名

    private String gender; //性别：0男 1女 2未知

    private String birth; //生日

    @Field(type = FieldType.Date)
    private Date createTime; //创建时间

    @Field(type = FieldType.Date)
    private Date updateTime; //更新时间

    private Boolean followed; //是否关注过

}
