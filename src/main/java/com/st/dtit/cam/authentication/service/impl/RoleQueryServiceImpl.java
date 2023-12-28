package com.st.dtit.cam.authentication.service.impl;

import com.st.dtit.cam.authentication.bean.bo.RoleBo;
import com.st.dtit.cam.authentication.bean.response.ItemData;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.constant.PropertyField;
import com.st.dtit.cam.authentication.constant.ResponseMessage;
import com.st.dtit.cam.authentication.dao.RoleDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.Role;
import com.st.dtit.cam.authentication.service.BaseService;
import com.st.dtit.cam.authentication.service.RoleQueryService;
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
public class RoleQueryServiceImpl extends BaseService implements RoleQueryService {

        private static final Logger LOGGER = LoggerFactory.getLogger(RoleQueryServiceImpl.class);

        @Autowired
        private RoleDao roleDao;

        @Override
        public Response getRolesByFilterCriteria(final Map<TableProperty, Object> filterCriteria, final String jwtToken) throws Exception {
                final String activeUser = getUserFromToken(jwtToken);
                LOGGER.info("start::{}::fetch roles by filter criteria: {}", activeUser, filterCriteria);

                if (MapUtils.isEmpty(filterCriteria)) {
                      LOGGER.warn("caution::{}::fetch roles by filter criteria::missing required parameter: {}", activeUser, PropertyField.FIELD_FILTER_CRITERIA);
                      return getValidationErrorResponse(ResponseMessage.ERROR_REQUIRED_PARAMETER, PropertyField.FIELD_FILTER_CRITERIA);
                }

                final List<Role> roles = roleDao.findByFilterCriteria(filterCriteria);
                if (CollectionUtils.isNotEmpty(roles)) {
                      final List<RoleBo> result = roles.stream()
                                                       .map(BoMapper::mapToRoleBo)
                                                       .collect(Collectors.toList());
                      return getResponse(new ItemData(result.size(), result));
                }
                LOGGER.debug("end::{}::fetch roles by filter criteria::no data", activeUser);
                return getEmptyDataResponse();
        }
}
