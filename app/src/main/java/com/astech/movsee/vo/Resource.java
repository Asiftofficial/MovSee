package com.astech.movsee.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.astech.movsee.vo.StatusResource.ERROR;
import static com.astech.movsee.vo.StatusResource.LOADING;
import static com.astech.movsee.vo.StatusResource.SUCCESS;

public class Resource<T> {

    @NonNull
    public final StatusResource status;

    @Nullable
    public final String msg;

    @Nullable
    public final T data;

    public Resource(@NonNull StatusResource status, @Nullable String msg, @Nullable T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Resource<T> loading(@Nullable T data){
        return new Resource<>(LOADING,null,data);
    }

    public static <T> Resource<T> success(@Nullable T data){
        return new Resource<>(SUCCESS,null,data);
    }

    public static <T> Resource<T> error(@Nullable String msg, @Nullable T data){
        return new Resource<>(ERROR,msg,data);
    }


}
