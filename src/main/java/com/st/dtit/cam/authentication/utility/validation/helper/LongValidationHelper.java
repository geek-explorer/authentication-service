package com.st.dtit.cam.authentication.utility.validation.helper;

import com.st.dtit.cam.authentication.utility.validation.FieldValidation;
import com.st.dtit.cam.authentication.utility.validation.Validation;

public class LongValidationHelper {

       public static Validation<Long> longValueNotNull = FieldValidation.from((i) -> null != i, "must not be null or empty.");

}
