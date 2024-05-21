package com.ddoddii.resume.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/*
- 클라이언트 요청 시 JWT 인증을 하기 위한 커스텀 필터
- 클라이언트로부터 들어오는 요청에서 JWT 토큰을 처리하고, 유효한 토큰인 경우 해당 토큰의 인증 정보(Authentication)를
SecurityContext에 저장하여 인증된 요청을 처리할 수 있도록 한다.
 */
@Slf4j
@Component
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_BEGIN_INDEX = 7;
    private TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 1. Request Header 에서 JWT Token 추출
        String token = resolveToken((HttpServletRequest) servletRequest);

        // 2. ValidateToken 으로 유효성 검사
        if (token != null && tokenProvider.validateToken(token)) {
            // 토큰이 유효한 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_BEGIN_INDEX);
        }
        return null;
    }

}
