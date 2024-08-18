package com.travel.travel.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
    
    //HTTP - 401: 인증이 필요함
    //commence: 인증되지 않은 접근이 발생했을때 호출됨
    //HttpServletRequest request: 사용자의 HTTP 요청을 나타냅니다. 인증되지 않은 사용자가 접근하려고 했던 요청에 대한 정보가 담겨 있습니다.
    //HttpServletResponse response: 서버가 클라이언트에게 보낼 HTTP 응답을 나타냅니다. 여기서 인증 실패 응답을 클라이언트에게 전달합니다.
    //AuthenticationException authException: 인증 과정에서 발생한 예외를 나타냅니다. 예를 들어, 자격 증명이 잘못되었거나 없는 경우 발생합니다.