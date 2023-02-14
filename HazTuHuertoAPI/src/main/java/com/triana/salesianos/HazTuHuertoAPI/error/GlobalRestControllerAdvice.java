package com.triana.salesianos.HazTuHuertoAPI.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triana.salesianos.HazTuHuertoAPI.error.model.impl.ApiErrorImpl;
import com.triana.salesianos.HazTuHuertoAPI.error.model.impl.ApiValidationSubError;
import com.triana.salesianos.HazTuHuertoAPI.security.errorhandling.JwtTokenException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;




@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex.getMessage(), request, status);
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return buildApiError(exception.getMessage(), request, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiErrorImpl.builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Constraint Validation error. Please check the sublist.")
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .subErrors(exception.getConstraintViolations().stream()
                                        .map(v -> {
                                            return ApiValidationSubError.builder()
                                                    .message(v.getMessage())
                                                    .rejectedValue(v.getInvalidValue())
                                                    .object(v.getRootBean().getClass().getSimpleName())
                                                    .field( ((PathImpl)v.getPropertyPath()).getLeafNode().asString())
                                                    .build();
                                        })
                                        .collect(Collectors.toList())
                                )
                                .build()
                );
    }




    private final ResponseEntity<Object> buildApiError(String message, WebRequest request, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(
                        ApiErrorImpl.builder()
                                .status(status)
                                .message(message)
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .build()
                );
    }

    private final ResponseEntity<Object> buildApiErrorWithSubErrors(String message, WebRequest request, HttpStatus status, List<ObjectError> subErrors) {
        return ResponseEntity
                .status(status)
                .body(
                        ApiErrorImpl.builder()
                                .status(status)
                                .message(message)
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .subErrors(subErrors.stream()
                                        .map(ApiValidationSubError::fromObjectError)
                                        .collect(Collectors.toList())
                                )
                                .build()
                );

    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer")
                .body(ErrorMessage.of(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI()));

    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorMessage.of(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI()));

    }


    @ExceptionHandler({JwtTokenException.class})
    public ResponseEntity<?> handleTokenException(JwtTokenException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorMessage.of(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUserNotExistsException(UsernameNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage.of(
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ErrorMessage {

        private HttpStatus status;
        private String message, path;

        @Builder.Default
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
        private LocalDateTime dateTime = LocalDateTime.now();

        public static ErrorMessage of (HttpStatus status, String message, String path) {
            return ErrorMessage.builder()
                    .status(status)
                    .message(message)
                    .path(path)
                    .build();
        }

    }



}
