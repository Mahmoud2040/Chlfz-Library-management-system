package com.maids.chelfz.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
