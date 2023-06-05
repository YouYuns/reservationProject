package com.reservation.config.auth;

import com.reservation.domain.User;
import com.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {
    private final UserRepository userRepository;


    // 시큐리티로 로그인이 될때, 시큐리티가 loadUserByUsername()실행해서 username를 체크!
    // 없으면 오류 꼭 InternalAuthenticationServiceException 이걸로 제어를해야된다.(인증실패)
    // 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new InternalAuthenticationServiceException("인증 실패"));
        return new LoginUser(user);
    }
}
