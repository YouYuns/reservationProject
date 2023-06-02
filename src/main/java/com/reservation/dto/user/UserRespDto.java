package com.reservation.dto.user;

import com.reservation.domain.User;
import com.reservation.util.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRespDto {

    @Getter
    @Setter
    public static class LoginRespDto{
        private Long id;
        private String email;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
        }
    }

    @ToString
    @Getter
    @Setter
    public static class JoinRespDto{
        private Long id;
        private String email;
        private  String name;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
        }
    }

}
