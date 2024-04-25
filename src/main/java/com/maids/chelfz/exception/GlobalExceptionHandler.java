package com.maids.chelfz.exception;


import com.maids.chelfz.util.response.ApiResponse;
import com.maids.chelfz.util.response.ApiResponseManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    private final ApiResponseManager<Void> apiResponseManager;

    public GlobalExceptionHandler(ApiResponseManager<Void> apiResponseManager) {
        this.apiResponseManager = apiResponseManager;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage()+"\n")
                .collect(Collectors.joining(", "));
        return apiResponseManager
                .failedResponse("Validation error: " + errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return apiResponseManager
                .failedResponse("Validation error: " + errorMessage);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<Void> handleNotFoundException(RecordNotFoundException ex) {
        String errorMessage = ex.getLocalizedMessage();
        return apiResponseManager
                .failedResponse( errorMessage);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponse<Void> handleConflictException(ConflictException ex) {
        String errorMessage = ex.getLocalizedMessage();
        return apiResponseManager
                .failedResponse(errorMessage);
    }


}
