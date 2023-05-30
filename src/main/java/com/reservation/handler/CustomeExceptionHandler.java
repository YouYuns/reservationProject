package com.reservation.handler;


import com.reservation.dto.ResponseDto;
import com.reservation.handler.ex.CustomApiException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class CustomeExceptionHandler {


    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> ApiException(CustomApiException e){
        log.debug(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }


}
