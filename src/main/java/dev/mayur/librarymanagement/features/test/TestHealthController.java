package dev.mayur.librarymanagement.features.test;

import dev.mayur.librarymanagement.core.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")

public class TestHealthController {
    @GetMapping
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Library Management System", null));
    }
}
