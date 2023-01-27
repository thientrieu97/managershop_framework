package com.util;

import com.exception.BusinessErrorCode;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ErrorCode {
    public static final BusinessErrorCode INTERNAL_SERVER_ERROR =
            new BusinessErrorCode(5000, "Internal server error", 503);
    public static final BusinessErrorCode INVALID_FIELD_FORMAT =
            new BusinessErrorCode(4013, "Invalid field format", 400);
    public static final BusinessErrorCode MISSING_PARAMETER =
            new BusinessErrorCode(4024, "Missing parameter", 400);
    public static final BusinessErrorCode FILE_TOO_LARGE =
            new BusinessErrorCode(4201, "File to large", 400);
    public static final BusinessErrorCode INVALID_PASSWORD_TYPE =
            new BusinessErrorCode(4162, "Incorrect password", 400);
    public static final BusinessErrorCode USER_NOT_FOUND =
            new BusinessErrorCode(4204, "User not found", 404);
    public static final BusinessErrorCode ROLE_NOT_FOUND =
            new BusinessErrorCode(4205, "Role not found", 404);
    public static final BusinessErrorCode ROLE_IS_ALREADY =
            new BusinessErrorCode(4206, "Role is already", 400);
    public static final BusinessErrorCode EMAIL_ALREADY =
            new BusinessErrorCode(4207, "Email already!", 400);
    public static final BusinessErrorCode EMAIL_IS_INVALID =
            new BusinessErrorCode(4220, "Email is invalid!", 400);
    public static final BusinessErrorCode PASSWORD_INVALID =
            new BusinessErrorCode(4208, "Password is invalid!", 400);
    public static final BusinessErrorCode UNAUTHORIZED =
            new BusinessErrorCode(4016, "You need to login to to access this resource", 401);

    public static final BusinessErrorCode PRODUCT_NOT_FOUND =
            new BusinessErrorCode(4209, "product not found", 404);

    public static final BusinessErrorCode PRODUCT_NOT_ENOUGH =
            new BusinessErrorCode(4210, "Product not ENOUGH", 404);
    static {
        Set<Integer> codes = new HashSet<>();
        var duplications = Arrays.stream(ErrorCode.class.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && f.getType().equals(BusinessErrorCode.class))
                .map(f -> {
                    try {
                        return ((BusinessErrorCode) f.get(null)).getCode();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(code -> !codes.add(code))
                .collect(Collectors.toList());
        if (!duplications.isEmpty()) {
            throw new RuntimeException("Duplicate error code: " + duplications);
        }
    }
}
