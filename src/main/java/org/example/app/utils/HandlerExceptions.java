package org.example.app.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.repository.impl.CustomerRepository;
import org.example.app.response.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class HandlerExceptions<T> {
    private static final Logger SERVICE_LOGGER =
            LogManager.getLogger(CustomerRepository.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    private static final String VALIDATION_FAILED = "Validation failed";
    private static final String FAILED_TO = "Failed to ";

    public ResponseEntity<ResponseTemplate<T>> handleException(String operation, Exception e) {
        SERVICE_LOGGER.error(FAILED_TO + "{}: {}", operation, e.getMessage());
        CONSOLE_LOGGER.error(FAILED_TO + "{}: {}", operation, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseTemplate<>(false, FAILED_TO + operation, null, Collections.singletonList(e.getMessage())));
    }

    public ResponseEntity<ResponseTemplate<T>> handleValidationErrors(List<String> errors) {
        SERVICE_LOGGER.error("Validation errors: {}", errors);
        CONSOLE_LOGGER.error("Validation errors: {}", errors);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseTemplate<>(false, VALIDATION_FAILED, null, errors));
    }

    public ResponseEntity<ResponseTemplate<T>> handleBadRequest(String operation) {
        SERVICE_LOGGER.error("Bad request: {}", operation);
        CONSOLE_LOGGER.error("Bad request: {}", operation);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseTemplate<>(false, "Id is required", null, List.of("Id is required")));
    }
}
