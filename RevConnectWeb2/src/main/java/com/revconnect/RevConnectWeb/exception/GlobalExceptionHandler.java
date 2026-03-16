package com.revconnect.RevConnectWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Handles all business-logic RuntimeExceptions */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred";

        HttpStatus status;

        // Map common message patterns to the right HTTP status
        if (message.contains("not found") || message.contains("Not found")) {
            status = HttpStatus.NOT_FOUND;
        } else if (message.contains("already") || message.contains("duplicate")
                || message.contains("exists") || message.contains("Cannot pin more than")) {
            status = HttpStatus.CONFLICT;
        } else if (message.contains("Only the receiver") || message.contains("can only")
                || message.contains("Cannot send request to yourself")
                || message.contains("cannot follow yourself")) {
            status = HttpStatus.FORBIDDEN;
        } else if (message.contains("already following") || message.contains("already shared")
                || message.contains("already liked") || message.contains("already pinned")) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(buildError(status, message));
    }

    /** Handles path variable type mismatches (e.g. /user/abc instead of /user/1) */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid parameter '" + ex.getName() + "': expected " +
                (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "correct type");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildError(HttpStatus.BAD_REQUEST, message));
    }

    /** Catch-all for any other exception */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
    }

    private Map<String, Object> buildError(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return body;
    }
}
