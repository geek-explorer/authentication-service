package com.st.dtit.cam.authentication.service.impl;

import com.st.dtit.cam.authentication.bean.bo.UserAuthenticationBo;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.constant.ResponseMessage;
import com.st.dtit.cam.authentication.dao.UserRoleDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.UserRoles;
import com.st.dtit.cam.authentication.service.ApplicationUserService;
import com.st.dtit.cam.authentication.service.BaseService;
import com.st.dtit.cam.authentication.utility.mapper.BoMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationUserServiceImpl extends BaseService implements ApplicationUserService {

        private static final long serialVersionUID = 9060471785561537834L;

        private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserServiceImpl.class);

        @Autowired
        private UserRoleDao userRoleDao;

        @Override
        public Response getUserDetailsAndRoles(final String userName, final String applicationName) throws Exception {
               if (StringUtils.isEmpty(userName)){
                     LOGGER.warn("user authentication error::missing required parameter:: {}", TableProperty.UR_USERNAME.getDescription());
                     return getValidationErrorResponse(ResponseMessage.ERROR_REQUIRED_PARAMETER, TableProperty.UR_USERNAME.getDescription());
               }

               if (StringUtils.isEmpty(applicationName)){
                     LOGGER.warn("user authentication error::missing required parameter:: {}", TableProperty.UR_APPLICATION.getDescription());
                     return getValidationErrorResponse(ResponseMessage.ERROR_REQUIRED_PARAMETER, TableProperty.UR_APPLICATION.getDescription());
               }

               final List<UserRoles> userRoles = getUserAndRoles(userName, applicationName);
               if (CollectionUtils.isNotEmpty(userRoles)){
                   final UserAuthenticationBo result  = BoMapper.mapToUserAuthenticationBo(userRoles);
                   return getResponse(result);
               }
               return getEmptyDataResponse();
        }

        private  List<UserRoles>  getUserAndRoles(String userName, String applicationName) throws HibernateException{
                 final Map<TableProperty, Object> filterCriteria = new HashMap<>();
                 filterCriteria.put(TableProperty.UR_APPLICATION, applicationName);
                 filterCriteria.put(TableProperty.UR_USERNAME, userName);
                 return userRoleDao.findUserRoles(filterCriteria);
        }
}
