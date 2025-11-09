package com.identityservice.common.exception;

public enum ErrorCode {

    // ---- 1. Common ----
    INTERNAL_SERVER_ERROR(1000, "Internal server error"),
    INVALID_REQUEST(1001, "Invalid request parameters"),
    UNAUTHORIZED(1002, "Unauthorized access"),
    FORBIDDEN(1003, "Access denied"),
    RESOURCE_NOT_FOUND(1004, "Resource not found"),
    METHOD_NOT_ALLOWED(1005, "HTTP method not allowed"),
    UNSUPPORTED_MEDIA_TYPE(1006, "Unsupported media type"),
    VALIDATION_FAILED(1007, "Validation failed"),

    UNKNOWN_ERROR(9999, "Unexpected runtime error"),

    // ---- 2. User-related ----
    USER_NOT_FOUND(2001, "User not found"),
    USER_EXISTED(2002, "User already exists"),
    INVALID_USER_CREDENTIALS(2003, "Invalid username or password"),

    // ---- 3. Auth-related ----
    TOKEN_EXPIRED(3001, "Token expired"),
    TOKEN_INVALID(3002, "Invalid token"),

    // ---- 4. Database / Server ----
    DATABASE_ERROR(4001, "Database operation failed"),
    SERVICE_UNAVAILABLE(4002, "Service temporarily unavailable"),

    // ---- 5. Role-related ----
    ROLE_NOT_FOUND(5001, "Role not found"),
    ROLE_EXISTED(5002, "Role already exists"),
    ROLE_ASSIGNMENT_FAILED(5003, "Failed to assign role to user"),
    ROLE_REMOVAL_FAILED(5004, "Failed to remove role from user"),
    ROLE_ACCESS_DENIED(5005, "User does not have the required role"),

    // ---- 6. Permission-related ----
    PERMISSION_NOT_FOUND(6001, "Permission not found"),
    PERMISSION_EXISTED(6002, "Permission already exists"),
    PERMISSION_ASSIGNMENT_FAILED(6003, "Failed to assign permission to role"),
    PERMISSION_REMOVAL_FAILED(6004, "Failed to remove permission from role"),
    PERMISSION_ACCESS_DENIED(6005, "User does not have the required permission");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
