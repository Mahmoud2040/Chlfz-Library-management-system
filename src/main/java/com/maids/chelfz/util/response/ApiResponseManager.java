package com.maids.chelfz.util.response;

import com.maids.chelfz.util.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiResponseManager<T> {

    public ApiResponse<T> successResponse(T data){

        return new ApiResponse<>("Success", "Success" , data);
    }
    public ApiResponse<Void> successMassage(String massage){
        return new ApiResponse<>("Success" , massage);
    }
    public ApiResponse<T> successMassageData(String massage,T data){
        return new ApiResponse<>("Success" , massage ,data);
    }

    public ApiResponse<Void> failedResponse(String errorMessage){
        return new ApiResponse<>("Failed" , errorMessage);
    }
}
