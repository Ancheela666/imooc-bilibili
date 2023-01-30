package com.imooc.bilibili.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//条件异常
public class ConditionException extends RuntimeException{

    private static final long serialVersionUID = 1L; //序列化时用的序列版本号

    private String code; //响应的状态码（比RuntimeException多出来的东西）

    public ConditionException(String code, String name){
        super(name);
        this.code = code;
    }

    public ConditionException(String name){
        super(name);
        code = "500";
    }
}
