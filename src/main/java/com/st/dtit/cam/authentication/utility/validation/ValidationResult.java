package com.st.dtit.cam.authentication.utility.validation;

import com.st.dtit.cam.authentication.bean.response.ValidationResponse;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.exception.FieldValidationException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class ValidationResult {

        private Boolean valid;

        private String message;

        public static ValidationResult ok(){
                return new ValidationResult(true, null);
        }

        public static ValidationResult fail(final String message){
                return new ValidationResult(false, message);
        }

        public ValidationResult(final Boolean valid, final String message){
                this.valid   = valid;
                this.message = message;
        }

        public  Boolean isValid(){
                return this.valid;
        }

        public  String getMessage(){
                return message;
        }

        public void throwIfValidationFail(){
               if (!isValid()) throw new FieldValidationException(getMessage());
        }

        public void throwIfValidationFail(final String responseMessage, final Object data){
              if (!isValid()){
                      ValidationResponse validationResponse = new ValidationResponse(ResponseCode.VALIDATION_ERROR, responseMessage, data);
                      throw new FieldValidationException(getMessage(), validationResponse);
              }
        }

        public void appendValidationResponseIfFail(List<ValidationResponse> validationResponseList, final String responseMessage, final Object data) {
                if (!isValid()){
                        ValidationResponse validationResponse = new ValidationResponse(ResponseCode.VALIDATION_ERROR, responseMessage, data);
                        validationResponseList.add(validationResponse);
                }
        }

        public void throwIfValidationErrorFound(List<ValidationResponse> validationResponse){
                if (CollectionUtils.isNotEmpty(validationResponse)) {
                        throw new FieldValidationException(getMessage(), validationResponse);
                }
        }


}
