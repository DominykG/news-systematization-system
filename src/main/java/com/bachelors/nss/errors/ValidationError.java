package com.bachelors.nss.errors;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationError {
    private final String fieldName;
    private final String errorMessage;
}
