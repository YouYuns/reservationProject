package com.reservation.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.config.auth.LoginUser;
import com.reservation.dto.user.UserReqDto;
import com.reservation.dto.user.UserRespDto;
import com.reservation.util.CustomResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private  AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    //Post : /login 하면 동작을한다
    // 컨트롤러 가기전의 필터이다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            ObjectMapper om = new ObjectMapper();
            //로그인의 데이터를 받아서 값이 담긴다.
            UserReqDto.LoginReqDto loginReqDto = om.readValue(request.getInputStream(), UserReqDto.LoginReqDto.class);

            //강제로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());

            //UserDetailsService의 loadUserByUsername 호출
            //JWT를 쓴다고해도 , 컨트롤러에 진입을 하면 시큐리티의 권한체크,인증체크의 도움을 받을 수 있게 세션을 만든다.
            //이 세션의유효기간은 request하고 response하면 끝!! (임시로 세션을 만드는것)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        }catch (Exception e){
//            authenticationEntryPoint 에 걸린다. 컨트롤러로 exception으로 넘길수 없기 때문에 여기로보내야된다.
            //unsuccessfulAuthentication를 호출하게된다. 그러니까 먼저 바로 로그인실패하면 unsuccessfulAuthentication를- >그리고여기로 exception
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response,"로그인실패", HttpStatus.UNAUTHORIZED);
    }
    //return authencation 잘 작동하면 successfulAuthentication 메서드가 호출된다.
    //토큰을 만들어낸다
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //로그인유저
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(JwtVO.HEADER,jwtToken);

        UserRespDto.LoginRespDto loginRespDto = new UserRespDto.LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }
}
