package com.nice.shop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("Login fail handler");
		String errorMessage;
		if (exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부적 발생한 시스템 문제";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage="계정 없음";
		} else if(exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage="인증 요청 거부";
		} else {
			errorMessage ="알 수없는 이유로 로그인 실패";
		}
		setDefaultFailureUrl("/auth/loginForm?error=true&exception="+errorMessage);
		super.onAuthenticationFailure(request, response, exception);
	}
}
