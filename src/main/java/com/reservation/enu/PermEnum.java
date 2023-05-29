package com.reservation.enu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PermEnum {

    SECRETAS("시크릿애즈펌"),
    VOLUME("볼륨펌"),
    SHADOW("쉐도우펌");


    @Getter
    private final String permName;


}
