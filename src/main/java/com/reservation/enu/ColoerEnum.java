package com.reservation.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ColoerEnum {

    ASHBLUE("애쉬블루"),
    ASHGRAY("애쉬그레이"),
    KHAKIBROWN("카키브라운");


    @Getter
    private String colorName;
}
