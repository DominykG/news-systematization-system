package com.bachelors.nss.rest.validation;

import com.bachelors.nss.errors.ValidationError;
import com.bachelors.nss.rest.models.UserRequest;

import java.util.HashSet;
import java.util.Set;

public final class UserRequestValidator {

    public static final String NAME_FIELD = "name";

    private static final Set<ValidationError> errors = new HashSet<>();

    public static Set<ValidationError> validateUserRequest(UserRequest request) {
        errors.clear();

        validateName(request);
//        validateSearchTerms(request);     //TODO: validate if at least one
//        validateExcludedTerms(request);   //TODO: validate if not longer than 255 char each
//        validateSources(request);         //TODO: validate if any of valid sources
//        validateFrom(request);            //TODO: validate if date <= today

        return errors;
    }

    private static void validateName(UserRequest request) {
        if (request.getName() == null) {
            errors.add(ValidationError.builder()
                    .fieldName(NAME_FIELD)
                    .errorMessage("Name must be present.")
                    .build());
        } else if (request.getName().length() < 3 || request.getName().length() > 20) {
            errors.add(ValidationError.builder()
                    .fieldName(NAME_FIELD)
                    .errorMessage("Name must be more than 3 and less than 20 characters long.")
                    .build());
        }
    }

}
