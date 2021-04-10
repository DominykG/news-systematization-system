package com.bachelors.nss.business.errors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationError {
    private final String fieldName;
    private final String errorMessage;

    public static ValidationError of(String fieldName, String errorMessage) {
        return new ValidationError(fieldName, errorMessage);
    }
}
