package com.reservation.config.jwt;

import com.reservation.config.auth.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 모든 주소에서 동작함 (토큰검증)
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        //Jwt
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(isHeaderVerify(request, response)){
            // 토큰검증 성공은아니고 토큰이 있다
            String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, ""); //공백으로 치환
            LoginUser loginUser = JwtProcess.verify(token);

            //임시 세션 (UserDetails 타입 or username)
            //detailsService 호출안하고 토큰을 강제로만들어버린다
            //객체 , 패스워드는 모르니까null, 권한
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                    null, loginUser.getAuthorities()); // id와 role만 담겨져있음
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //chain.doFilter를 if문밖에둠으로써 토큰이없다고 필터를못타게(즉, 시큐리티 자체에 도달도못하게 하는것보다 시큐리티에게 권한체크는 하게두는게좋다)
        // 이래야지 토큰이없어도 필터는 통과하고 권한문제는시큐리티가 해결가능하다

        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response){
        String header = request.getHeader(JwtVO.HEADER);

        //널이거나 헤더의 시작값이 Bearer 이 아니면
        if(header ==null || !header.startsWith(JwtVO.TOKEN_PREFIX)){
            return false;
        }else{
            return true;
        }
    }
}
