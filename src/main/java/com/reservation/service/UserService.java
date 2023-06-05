package com.reservation.service;


import com.reservation.domain.User;
import com.reservation.dto.user.UserReqDto;
import com.reservation.dto.user.UserRespDto;
import com.reservation.handler.ex.CustomApiException;
import com.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserRespDto.JoinRespDto join(UserReqDto.JoinReqDto joinReqDto){
        Optional<User> userOp = userRepository.findByEmail(joinReqDto.getEmail());
        if(userOp.isPresent()){
            throw new CustomApiException("동일안 이메일이 가입되어있습니다.");
        }

        User user = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        return new UserRespDto.JoinRespDto(user);


    }
}
