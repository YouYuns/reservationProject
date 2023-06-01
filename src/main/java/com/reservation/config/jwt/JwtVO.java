package com.reservation.config.jwt;

public interface JwtVO {
    /**
     * SECRET는 노출되면안된다.(환경변수 클라우스 AWS, 파일에 두고 써야된다)
     * REFRESH TOKEN (구현지금은 안함)
     */
    public static final String SECRET ="윤성호"; //HS256( 대칭키 )
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; //일주일
    public static final String TOKEN_PREFIX = "Bearer "; //한칸띄어야됨
    public static final String HEADER = "Authorization";

}
