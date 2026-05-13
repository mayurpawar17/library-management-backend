package dev.mayur.librarymanagement.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

import dev.mayur.librarymanagement.core.dto.ApiResponse;
import org.springframework.lang.NonNull;
import tools.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(@NonNull HttpServletRequest request,
                       HttpServletResponse response,
                       @NonNull AccessDeniedException accessDeniedException) throws IOException {

        // 1. Create your consistent response object using your builder/helper
        ApiResponse<Object> apiResponse = ApiResponse.error(
                "Access Denied: You do not have permission to access this resource.",
                "AUTH_FORBIDDEN"
        );

        // 2. Set the response properties
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        // 3. Write the JSON string to the response body
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }
}
