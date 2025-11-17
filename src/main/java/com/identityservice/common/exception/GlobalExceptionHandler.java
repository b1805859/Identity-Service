package com.identityservice.common.exception;

import com.identityservice.dto.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to handle all application-wide exceptions. This class ensures consistent
 * API responses for both expected and unexpected errors.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle unexpected RuntimeExceptions (uncaught system errors). (new3)
   *
   * @param ex the exception thrown
   * @return standardized API error response
   */
  @ExceptionHandler(value = RuntimeException.class)
  public ApiResponse<Void> handleGeneralException(Exception ex) {
    ApiResponse<Void> apiResponse = new ApiResponse<>();
    apiResponse.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
    apiResponse.setMessage(ErrorCode.UNKNOWN_ERROR.getMessage());
    return apiResponse;
  }

  /**
   * Handle custom application exceptions (AppException) thrown intentionally.
   *
   * @param ex the AppException instance
   * @return standardized API response containing the specific error code and message
   */
  @ExceptionHandler(value = AppException.class)
  public ApiResponse<Void> handleAppException(AppException ex) {
    ApiResponse<Void> apiResponse = new ApiResponse<>();
    apiResponse.setCode(ex.getErrorCode().getCode());
    apiResponse.setMessage(ex.getErrorCode().getMessage());
    return apiResponse;
  }

  /**
   * Handle validation errors triggered by @Valid annotations in request DTOs. Collects all
   * field-level validation messages into a structured map.
   *
   * @param ex the MethodArgumentNotValidException thrown during validation
   * @return API response containing a list of field-specific error messages
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ApiResponse<Map<String, String>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    // Extract validation error messages and associate them with field names
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );

    // Build standardized API response
    ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>();
    apiResponse.setCode(ErrorCode.INVALID_REQUEST.getCode());
    apiResponse.setMessage(ErrorCode.INVALID_REQUEST.getMessage());
    apiResponse.setResult(errors);

    return apiResponse;
  }
}
