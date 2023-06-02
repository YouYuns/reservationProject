package com.reservation.dto.user;

import com.reservation.domain.User;
import com.reservation.enu.UserEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {

    @Getter
    @Setter
    public static class LoginReqDto{
        private String email;
        private String password;
    }


    @Setter
    @Getter
    public static class JoinReqDto{
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty // null이거나 , 공백일 수 없다
        private String email;

        @NotEmpty // null이거나 , 공백일 수 없다
        @Size(min = 4, max = 20)
        private String password;

        @NotEmpty // null이거나 , 공백일 수 없다
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
        private String name;

        @NotEmpty
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 형식으로 입력하세요")
        private String tel;

        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            return User.builder()
                    .email(email)
                    .passward(passwordEncoder.encode(password))
                    .name(name)
                    .tel(tel)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}
