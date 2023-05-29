package com.reservation.config;


import com.schedulereservation.config.jwt.JwtAuthenticationFilter;
import com.schedulereservation.config.jwt.JwtAuthorizationFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@Log4j2
public class SecurityConfig {

    private BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 : BCryptPasswordEncoder 등록");
        return new BCryptPasswordEncoder();

    }
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        //필터만들기
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.headers().frameOptions().disable(); //iframe 이용안함 -> 웹사이트안에 다른웹사이트 열지못하게
        http.csrf().disable(); //enable이면 post맨 작동안함
        http.cors().configurationSource(corsConfigurationSource());

        //jSessionId를 서버 쪽에서 관리안하겠다
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //react, 앱으로 요청할 예정
        http.formLogin().disable();
        //httpBasic 은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
        http.httpBasic().disable();

        //Jwt 필터 적용
        http.apply(new CustomSecurityFilterManager());

        // 인증실패
//        http.exceptionHandling().authenticationEntryPoint((request, response, authenticationException)->{
//            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
//        });


        //권한실패
//        http.exceptionHandling().accessDeniedHandler(((request, response, e) -> {
//            CustomResponseUtil.fail(response,"권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }));


//        http.authorizeRequests()
//                .antMatchers("/api/s/**").authenticated()
//                .antMatchers("api/admin/**").hasRole("" + UserEnum.ADMIN) // prefix로 ROLE_이 들어감
//                .anyRequest().permitAll();
//
        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource(){
        log.debug("corsConfigurationSource 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); //GET, POST, PUT,DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); //모든 IP 주소 허용 (프론트 엔드 IP만 허용 react)
        configuration.setAllowCredentials(true); //클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //모든 주소요청에 configuration설정을 넣어주겠다.
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
