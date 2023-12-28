package com.st.dtit.cam.authentication.utility.validation.helper;

import com.st.dtit.cam.authentication.constant.CommonConstant;
import com.st.dtit.cam.authentication.utility.validation.FieldValidation;
import com.st.dtit.cam.authentication.utility.validation.Validation;
import com.st.dtit.cam.authentication.utility.validation.ValidationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;

public class StringValidationHelper {

       public static Validation<String> notNullOrEmpty      = FieldValidation.from((s) -> StringUtils.isNotEmpty(s), "must not be null or empty string.");

       public static Validation<String> nonDeleteEntryValue = FieldValidation.from((s) -> !CommonConstant.VALUE_REMOVE_VALUE.equalsIgnoreCase(s), "must not be equal to delete entry value.");

       public static Validation<String> validEmailAddress   = FieldValidation.from((s) -> ValidationUtil.validEmailAddress((String) s), "is not a valid email address.");

       public static Validation<String> validPhoneNumber    = FieldValidation.from((s) -> ValidationUtil.validPhoneNumber((String) s), "is not a valid phone number.");

       public static Validation<String> validNumericValues  = FieldValidation.from((s) -> StringUtils.isNumeric(s), " is not valid numeric value");

       public static Validation<List<String>> notEmptyList  = FieldValidation.from((s) -> CollectionUtils.isNotEmpty((List<String>) s), "must not an empty list.");

       public static Validation<String> contains(String c){
            return FieldValidation.from((s) -> s.contains(c), format("must contains %s", c));
       }

       public static Validation<String> hasValidValues(String[] comparator){
            return FieldValidation.from((s) -> Stream.of(comparator).anyMatch(x -> x.equals(s)), format(" values must be equals to either from the list %s", comparator.toString()));
       }

       public static <E extends Enum<E>> Validation<String> hasValidValues(Class<E> enumClass){
            return FieldValidation.from((s) -> EnumUtils.isValidEnumIgnoreCase(enumClass, (String) s), format(" values must be equals to defined enum values %s", enumClass.getClass()));
       }



}
