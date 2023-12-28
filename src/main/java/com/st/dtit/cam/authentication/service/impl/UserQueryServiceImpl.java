package com.st.dtit.cam.authentication.service.impl;

import com.st.dtit.cam.authentication.bean.bo.ApplicationUserBo;
import com.st.dtit.cam.authentication.bean.response.ItemData;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.constant.PropertyField;
import com.st.dtit.cam.authentication.constant.ResponseMessage;
import com.st.dtit.cam.authentication.dao.UserDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import com.st.dtit.cam.authentication.service.BaseService;
import com.st.dtit.cam.authentication.service.UserQueryService;
import com.st.dtit.cam.authentication.utility.mapper.BoMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserQueryServiceImpl extends BaseService implements UserQueryService {

        private static final Logger LOGGER = LoggerFactory.getLogger(UserQueryServiceImpl.class);

        @Autowired
        private UserDao userDao;

        @Override
        public Response getAllAuthenticationUsers(final String jwtToken) throws Exception {
                final String activeUser = getUserFromToken(jwtToken);
                LOGGER.debug("start::{}::fetch all authentication users", activeUser);

                final List<ApplicationUser>  applicationUsers = userDao.findAll();
                LOGGER.debug("response::{}::fetch all authentication users: {}", activeUser, applicationUsers);
                if (CollectionUtils.isNotEmpty(applicationUsers)) {
                    final List<ApplicationUserBo>  result = applicationUsers.stream()
                                                                            .map(BoMapper::mapToApplicationUserBo)
                                                                            .collect(Collectors.toList());

                    return getResponse(new ItemData(result.size(), result));
                }
                LOGGER.debug("end::{}::fetch all authentication users::no data", activeUser);
                return getEmptyDataResponse();
        }

        @Override
        public Response getAuthenticationUsersByFilterCriteria(final Map<TableProperty, Object> filterCriteria, final String jwtToken) throws Exception {
                final String activeUser = getUserFromToken(jwtToken);
                LOGGER.debug("start::{}::fetch authentication users by filter criteria: {}", activeUser, filterCriteria);

                if (MapUtils.isEmpty(filterCriteria)) {
                      LOGGER.warn("caution::{}::fetch authentication users by filter criteria::missing required parameter: {}", activeUser, PropertyField.FIELD_FILTER_CRITERIA);
                      return getValidationErrorResponse(ResponseMessage.ERROR_REQUIRED_PARAMETER, PropertyField.FIELD_FILTER_CRITERIA);
                }

                final List<ApplicationUser> applicationUsers = userDao.findByFilterCriteria(filterCriteria);
                LOGGER.debug("response::{}::fetch authentication users by filter criteria: {}", activeUser, applicationUsers);
                if (CollectionUtils.isNotEmpty(applicationUsers)) {
                    final List<ApplicationUserBo>  result = applicationUsers.stream()
                                                                            .map(BoMapper::mapToApplicationUserBo)
                                                                            .collect(Collectors.toList());

                    return getResponse(new ItemData(result.size(), result));
                }
                LOGGER.debug("end::{}::fetch authentication users by filter criteria::no data", activeUser);
                return getEmptyDataResponse();
        }
}
