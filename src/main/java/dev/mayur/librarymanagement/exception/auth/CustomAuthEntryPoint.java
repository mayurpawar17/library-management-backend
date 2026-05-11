package dev.mayur.librarymanagement.exception.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
// ── 401 Handler — triggered when the request has NO valid token ────────────────
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    // AuthenticationEntryPoint is called when Spring Security cannot authenticate
    // the request — i.e., missing token, expired token, malformed token

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Set HTTP 401 Unauthorized status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Tell the client we're returning JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Write a structured JSON error body
        // Without this, Spring returns an HTML error page — not API-friendly
        Map<String, Object> body = Map.of(
                "status", 401,
                "error", "Unauthorized",
                "message", "Missing or invalid JWT token. Please login first.",
                "timestamp", System.currentTimeMillis()
        );

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
