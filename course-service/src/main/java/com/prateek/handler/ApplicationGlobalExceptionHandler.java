package com.prateek.handler;

import com.prateek.dto.ErrorDTO;
import com.prateek.dto.ServiceResponse;
import com.prateek.exception.CourseServiceBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServiceResponse<?> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    errorDTOList.add(new ErrorDTO(error.getField() + " : " + error.getDefaultMessage()));
                });
        serviceResponse.setError(errorDTOList);
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);

        return serviceResponse;
    }

    @ExceptionHandler(CourseServiceBusinessException.class)
    public ServiceResponse<?> handleCourseServiceBusinessException(CourseServiceBusinessException ex) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        serviceResponse.setError(List.of(new ErrorDTO(ex.getMessage())));
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
        return serviceResponse;
    }
}
