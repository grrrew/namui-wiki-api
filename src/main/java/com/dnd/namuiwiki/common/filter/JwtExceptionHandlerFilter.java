package com.dnd.namuiwiki.common.filter;

import com.dnd.namuiwiki.common.exception.ApplicationErrorType;
import com.dnd.namuiwiki.common.exception.dto.ErrorResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    private final String[] allowedOrigins;

    private final List<String> excludeUrlPatterns;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(ApplicationErrorType.AUTHENTICATION_FAILED.name(), e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");

            String origin = request.getHeader("origin");
            if (Arrays.asList(allowedOrigins).contains(origin)) {
                response.setHeader("Access-Control-Allow-Origin", origin);
            }

            response.getWriter().write(convertObjectToJson(errorResponseDto));
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return objectMapper.writeValueAsString(object);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludeUrlPatterns.stream()
                .anyMatch(p -> antPathMatcher.match(p, request.getServletPath()));
    }
}
