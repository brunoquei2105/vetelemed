package br.com.vetelemed.api.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class JwtCookieService {

    public void addJwtToCookie(String jwt, HttpServletResponse response ){
        ResponseCookie cookie = ResponseCookie.from("token-jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7200000) // 1 day
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
