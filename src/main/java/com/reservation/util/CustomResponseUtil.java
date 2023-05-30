package com.reservation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.dto.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;


//ExceptionHandler 가기 전에 예외처리
@Log4j2
public class CustomResponseUtil {
    public static void success(HttpServletResponse response, Object dto){
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?>  responseDto = new ResponseDto<>(1, "로그인 성공", dto);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);

        }catch (Exception e){
            log.debug("서버 파싱 에러");
        }
    }
    public static void fail(HttpServletResponse response, String msg, HttpStatus status){
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?>  responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(status.value());
            response.getWriter().println(responseBody);

        }catch (Exception e){
            log.debug("서버 파싱 에러");
        }
    }
}
