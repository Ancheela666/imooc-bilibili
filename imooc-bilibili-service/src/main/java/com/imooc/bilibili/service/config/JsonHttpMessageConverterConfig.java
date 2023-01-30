package com.imooc.bilibili.service.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class JsonHttpMessageConverterConfig {

    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat, //格式化输出
                SerializerFeature.WriteNullStringAsEmpty, //把null字段变成空字符串而不是直接去掉
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.MapSortField, //对map类型的键值对数据进行升序排序
                SerializerFeature.DisableCircularReferenceDetect //禁用循环引用
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //如果使用feign进行微服务之间的接口调用，则需要加上以下配置
        fastConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON)); //支持json类型的数据的传输
        return new HttpMessageConverters(fastConverter);
    }

    //循环引用展示
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        Object o = new Object();
        list.add(o);
        list.add(o);
        System.out.println(list.size());
        //默认状态
        System.out.println(JSONObject.toJSONString(list));
        //禁用循环引用
        System.out.println(JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
    }
}
