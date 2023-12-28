package com.st.dtit.cam.authentication.utility.validation.helper;


import com.st.dtit.cam.authentication.utility.validation.FieldValidation;
import com.st.dtit.cam.authentication.utility.validation.Validation;

public class IntegerValidationHelper {

       public static Validation<Integer> integerValueNotNull = FieldValidation.from((i) -> null != i, "must not be null or empty.");
}
