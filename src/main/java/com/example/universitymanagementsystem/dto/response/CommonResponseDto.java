package com.example.universitymanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommonResponseDto<T> {
    private T data;
    private Object message;
    private Integer status;

    public T getData() {
        return data;
    }

    public CommonResponseDto<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Object getMessage() {
        return message;
    }

    public CommonResponseDto<T> setMessage(Object message) {
        this.message = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public CommonResponseDto<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public CommonResponseDto<T> setOk(){
        this.message="OK";
        this.status=200;
        return this;
    }
}
