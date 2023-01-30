package com.imooc.bilibili.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "videos")
public class Video {
    @Id //标识主键id
    private Long id; //主键id

    @Field(type = FieldType.Long) //标识该字段在es中的存储格式
    private Long userId; //用户id

    private String url; //视频链接

    private String thumbnail; //封面

    @Field(type = FieldType.Text) //可以进行分词查询
    private String title; //标题

    private String type; //视频类型： 0自制 1转载

    private String duration; //时长

    private String area; //分区

    private List<VideoTag> videoTagList; //标签列表

    @Field(type = FieldType.Text)
    private String description; //简介

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;
}
