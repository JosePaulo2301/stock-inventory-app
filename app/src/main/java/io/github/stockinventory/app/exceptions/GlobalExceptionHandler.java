package io.github.stockinventory.app.exceptions;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandler {
    private final Map<Class<? extends Exception >, Function<Exception, ResponseEntity<ProblemDetail>>> exceptionHandlers = Map.of(
        ResourceNotFoundException.class, ex -> createResponse(ex, HttpStatus.NOT_FOUND)
    );


public ResponseEntity<ProblemDetail> handleException(Exception ex) {
    return exceptionHandlers
    .getOrDefault(ex.getClass(), e -> createResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR))
    .apply(ex);
}

private static ResponseEntity<ProblemDetail> createResponse(Exception ex, HttpStatus status) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    return ResponseEntity.status(status).body(problemDetail);
 }
}