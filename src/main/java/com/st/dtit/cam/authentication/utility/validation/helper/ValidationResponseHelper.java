package com.st.dtit.cam.authentication.utility.validation.helper;

import com.st.dtit.cam.authentication.bean.response.ValidationResponse;
import com.st.dtit.cam.authentication.utility.validation.FieldValidation;
import com.st.dtit.cam.authentication.utility.validation.Validation;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class ValidationResponseHelper {

       public static Validation<Boolean> entityFoundException = FieldValidation.from((s) -> s == false, " entity already exist");

       public static Validation<Boolean>  entityNotFoundException = FieldValidation.from((s) -> s == true, " entity not exist");

       public static Validation<List<ValidationResponse>> hasValidationError = FieldValidation.from((s) -> CollectionUtils.isNotEmpty(s), " validation error found");
}
