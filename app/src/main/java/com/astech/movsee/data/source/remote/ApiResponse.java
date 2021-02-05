package com.astech.movsee.data.source.remote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.astech.movsee.data.source.remote.StatusResponse.EMPTY;
import static com.astech.movsee.data.source.remote.StatusResponse.ERROR;
import static com.astech.movsee.data.source.remote.StatusResponse.SUCCESS;

public class ApiResponse<T> {

    @NonNull
    public final StatusResponse status;

    @Nullable
    public final String message;

    @Nullable
    public final T body;

    public ApiResponse(@NonNull StatusResponse status, @Nullable String message, @Nullable T body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public static <T> ApiResponse<T> success(@Nullable T body){
        return new ApiResponse<>(SUCCESS,null,body);
    }

    public static <T> ApiResponse<T> empty(@Nullable T body){
        return new ApiResponse<>(EMPTY,null,body);
    }

    public static <T> ApiResponse<T> error(@Nullable String msg,@Nullable T body){
        return new ApiResponse<>(ERROR,msg,body);
    }
}
