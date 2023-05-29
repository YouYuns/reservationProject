package com.reservation.enu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CutEnum {

    DANDY("댄디컷"),
    TWOBLOCK("투블럭"),
    CROP("크롭컷");

    private final String cutName;
}
