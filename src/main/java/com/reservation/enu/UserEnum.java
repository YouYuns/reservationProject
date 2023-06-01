package com.reservation.enu;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserEnum {

    ADMIN("관리자"), CUSTOMER("고객");

    @Getter
    private final String value;

}
