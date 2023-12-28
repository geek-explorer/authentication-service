package com.st.dtit.cam.authentication.utility.validation;

import java.util.function.Predicate;

public class FieldValidation<K> implements Validation<K> {

    private Predicate<K> predicate;

    private String onErrorMessage;

    public static <K> FieldValidation<K> from(Predicate<K> predicate, String onErrorMessage){
            return new FieldValidation<K>(predicate, onErrorMessage);
    }

    private FieldValidation(Predicate<K> predicate, String onErrorMessage){
           this.predicate = predicate;
           this.onErrorMessage = onErrorMessage;
    }

    @Override
    public ValidationResult validate(K param) {
        return predicate.test(param) ? ValidationResult.ok() : ValidationResult.fail(onErrorMessage);
    }
}
