package com.reservation.domain;


import com.reservation.config.BaseEntity;
import com.reservation.enu.UserEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
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
    private Long tel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

}
