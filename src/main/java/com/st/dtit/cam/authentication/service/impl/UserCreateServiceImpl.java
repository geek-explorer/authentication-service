package com.st.dtit.cam.authentication.service.impl;

import com.st.dtit.cam.authentication.bean.bo.ApplicationUserBo;
import com.st.dtit.cam.authentication.bean.param.ApplicationUserParameter;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.constant.PropertyField;
import com.st.dtit.cam.authentication.constant.ResponseMessage;
import com.st.dtit.cam.authentication.dao.UserDao;
import com.st.dtit.cam.authentication.exception.FieldValidationException;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import com.st.dtit.cam.authentication.service.BaseService;
import com.st.dtit.cam.authentication.service.UserUpdateService;
import com.st.dtit.cam.authentication.utility.mapper.BoMapper;
import com.st.dtit.cam.authentication.utility.mapper.EntityMapper;
import com.st.dtit.cam.authentication.utility.validation.InputParamValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("authUserCreate")
public class UserCreateServiceImpl extends BaseService implements UserUpdateService {

        private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateServiceImpl.class);

        @Autowired
        private UserDao userDao;

        @Autowired
        @Qualifier("userCreateValidation")
        private InputParamValidation inputParamValidation;

        @Override
        public Response updateApplicationUser(ApplicationUserParameter inputParameter, String jwtToken) throws Exception {
                final String activeUser = getUserFromToken(jwtToken);
                LOGGER.debug("start::{}::insert authentication user::parameter: {}", activeUser, inputParameter);

                if (null == inputParameter || null == inputParameter.getAuthenticationUser()) {
                     LOGGER.warn("caution::{}::insert authentication user information::missing required parameter: {}", activeUser, PropertyField.FIELD_INPUT_PARAMETER);
                     return getValidationErrorResponse(ResponseMessage.ERROR_REQUIRED_PARAMETER, PropertyField.FIELD_INPUT_PARAMETER);
                }

                ApplicationUserBo result = null;
                try {
                    inputParamValidation.validate(inputParameter.getAuthenticationUser(), jwtToken);
                    ApplicationUser applicationUser = new ApplicationUser();

                    applicationUser = EntityMapper.mapToApplicationUserEntity(applicationUser, inputParameter.getAuthenticationUser());
                    applicationUser.setCreatedBy(activeUser);
                    applicationUser.setDateCreated(new Date());

                    applicationUser = userDao.insert(applicationUser);
                    result = BoMapper.mapToApplicationUserBo(applicationUser);
                } catch (FieldValidationException val_ex) {
                    LOGGER.warn("cation::{}::insert authentication user::validation exception: {}", activeUser, val_ex.getResponse());
                    return this.validationErrorResponse(val_ex.getResponse());
                } finally {
                    LOGGER.debug("end::{}::insert application user::result: {}", activeUser, result);
                }
                return getResponse(result);
        }
}
