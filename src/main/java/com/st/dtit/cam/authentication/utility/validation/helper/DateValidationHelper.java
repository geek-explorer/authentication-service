package com.st.dtit.cam.authentication.utility.validation.helper;


import com.st.dtit.cam.authentication.utility.validation.FieldValidation;
import com.st.dtit.cam.authentication.utility.validation.Validation;

import java.util.Date;

public class DateValidationHelper {

    public static Validation<Date> dateValueNotNull = FieldValidation.from((i) -> null != i, "must not be null or empty.");

}
