package com.st.dtit.cam.authentication.utility.validation;

import com.st.dtit.cam.authentication.bean.bo.ApplicationUserBo;
import com.st.dtit.cam.authentication.bean.response.ValidationResponse;
import com.st.dtit.cam.authentication.constant.PropertyField;
import com.st.dtit.cam.authentication.dao.UserDao;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.st.dtit.cam.authentication.constant.ResponseMessage.ERROR_ENTITY_ALREADY_EXIST;
import static com.st.dtit.cam.authentication.constant.ResponseMessage.ERROR_INVALID_EMAIL_FORMAT;
import static com.st.dtit.cam.authentication.constant.ResponseMessage.ERROR_REQUIRED_FIELDS;
import static com.st.dtit.cam.authentication.utility.validation.helper.StringValidationHelper.notNullOrEmpty;
import static com.st.dtit.cam.authentication.utility.validation.helper.StringValidationHelper.validEmailAddress;
import static com.st.dtit.cam.authentication.utility.validation.helper.ValidationResponseHelper.hasValidationError;

@Component("userCreateValidation")
public class UserCreateValidation implements InputParamValidation{

        private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateValidation.class);

        @Autowired
        private UserDao userDao;

        private List<ValidationResponse> validationResponse;

        @Override
        public void validate(final Object objectBean, final String jwtToken) {
               final ApplicationUserBo applicationUserBo = (ApplicationUserBo) objectBean;
               validationResponse = new ArrayList<>();

               validateRequiredFields(applicationUserBo);
               validateInputFields(applicationUserBo);

               hasValidationError.validate(validationResponse).throwIfValidationErrorFound(validationResponse);
        }

        private void validateRequiredFields(ApplicationUserBo applicationUserBo) {
                notNullOrEmpty.validate(applicationUserBo.getUserId()).appendValidationResponseIfFail(validationResponse, ERROR_REQUIRED_FIELDS, TableProperty.USER_ID.getDescription());
                notNullOrEmpty.validate(applicationUserBo.getUserName()).appendValidationResponseIfFail(validationResponse, ERROR_REQUIRED_FIELDS, TableProperty.USER_USERNAME.getDescription());
                notNullOrEmpty.validate(applicationUserBo.getLastName()).appendValidationResponseIfFail(validationResponse,ERROR_REQUIRED_FIELDS, TableProperty.LAST_NAME.getDescription());
                notNullOrEmpty.validate(applicationUserBo.getFirstName()).appendValidationResponseIfFail(validationResponse,ERROR_REQUIRED_FIELDS, TableProperty.FIRST_NAME.getDescription());
                notNullOrEmpty.validate(applicationUserBo.getEmailAddress()).appendValidationResponseIfFail(validationResponse,ERROR_REQUIRED_FIELDS, TableProperty.EMAIL_ADDRESS.getDescription());
        }

        private void validateInputFields(ApplicationUserBo applicationUserBo) {
                if (CollectionUtils.isNotEmpty(validationResponse)) {
                    return;
                }
                validEmailAddress.validate(applicationUserBo.getEmailAddress()).appendValidationResponseIfFail(validationResponse, ERROR_INVALID_EMAIL_FORMAT, TableProperty.EMAIL_ADDRESS.getDescription());
                try {
                     ApplicationUser applicationUser = userDao.findById(applicationUserBo.getUserId());

                     if (null != applicationUser) {
                        validationResponse.add(new ValidationResponse(ResponseCode.VALIDATION_ERROR, ERROR_ENTITY_ALREADY_EXIST,
                                               new String[] {PropertyField.ENTITY_AUTHENTICATION_USER, applicationUserBo.getUserId()}));
                     } else {

                        final Map<TableProperty, Object> filterCriteria = new HashMap<>();
                        filterCriteria.put(TableProperty.USER_USERNAME, applicationUserBo.getUserName());

                        Optional<ApplicationUser> applicationUserOptional = userDao.findByFilterCriteria(filterCriteria).stream().findAny();
                        if (applicationUserOptional.isPresent()) {
                            validationResponse.add(new ValidationResponse(ResponseCode.VALIDATION_ERROR, ERROR_ENTITY_ALREADY_EXIST,
                                                   new String[] {PropertyField.ENTITY_AUTHENTICATION_USER, applicationUserBo.getUserName()}));
                        }
                     }
                } catch (HibernateException hib_ex) {
                     LOGGER.error("error::authentication user input validation error::", hib_ex);
                }
        }
}
