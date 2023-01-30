package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse<T> {

    private String code; //返回的状态码

    private String msg; //返回的提示语

    private T data; //返回的数据

    public JsonResponse(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data){
        this.data = data;
        code = "成功";
        msg = "0"; //0代表成功，非0代表失败
    }

    public static JsonResponse<String> success(){ //返回单纯的成功信息
        return new JsonResponse<>(null);
    }

    public static JsonResponse<String> success(String data){ //返回成功信息与参数
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> fail(){ //返回失败信息with通用的状态码与提示信息
        return new JsonResponse<>("1", "失败");
    }

    public static JsonResponse<String> fail(String code, String msg){ //返回失败信息with特定的状态码与提示信息
        return new JsonResponse<>(code, msg);
    }
}
