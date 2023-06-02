package com.reservation.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reservation.config.auth.LoginUser;
import com.reservation.domain.User;
import com.reservation.enu.UserEnum;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Date;


public class JwtProcess {

    // 토큰 생성
    public static String create(LoginUser loginUser){
        String jwtToken = JWT.create()
                .withSubject("reservation") //아무거나 적어도됨 제목임
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME)) //현재시간 + 만료시간 (일주일) 까지 유효할거다
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().getValue())
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return JwtVO.TOKEN_PREFIX+jwtToken;
    }

    // 토큰 검증 (return 되는 LoginUser객체를 강제로 시큐리티 세션에 직접 주입할 예정) -> 강제로그인
    public static LoginUser verify(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();

        //role = "CUSTOMER" 문자열
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}
