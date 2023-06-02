package com.reservation.domain;


import com.reservation.config.BaseEntity;
import com.reservation.enu.UserEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Table(name = "user_tb")
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String email;

    @Column(nullable = false)
    private String passward;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

    @Builder
    public User(Long id, String email, String passward, String name, String tel, UserEnum role) {
        this.id = id;
        this.email = email;
        this.passward = passward;
        this.name = name;
        this.tel = tel;
        this.role = role;
    }
}
