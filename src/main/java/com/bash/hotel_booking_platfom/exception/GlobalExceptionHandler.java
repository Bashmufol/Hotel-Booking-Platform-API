package com.bash.hotel_booking_platfom.exception;

import com.bash.hotel_booking_platfom.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * Ensures consistent error responses for different types of exceptions.
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleGenericException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorResponseDto handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseDto handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ErrorResponseDto handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), request, HttpStatus.CONFLICT);
    }


    /**
     * Helper method to build a standardized error response.
     */
    private ErrorResponseDto buildErrorResponse(String message, HttpServletRequest request, HttpStatus status) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(message);
        error.setTimeStamp(LocalDateTime.now());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        return error;
    }
}
