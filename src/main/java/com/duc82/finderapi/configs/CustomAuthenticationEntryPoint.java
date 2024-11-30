package com.duc82.finderapi.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper jacksonObjectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;

        Map<String, Object> error = Map.of(
                "status", status,
                "message", authException.getMessage(),
                "timestamp", System.currentTimeMillis()
        );

        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(jacksonObjectMapper.writeValueAsString(error));
    }
}
