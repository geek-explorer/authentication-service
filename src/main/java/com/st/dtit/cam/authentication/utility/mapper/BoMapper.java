package com.st.dtit.cam.authentication.utility.mapper;

import com.st.dtit.cam.authentication.bean.bo.ApplicationServiceBo;
import com.st.dtit.cam.authentication.bean.bo.ApplicationUserBo;
import com.st.dtit.cam.authentication.bean.bo.RoleBo;
import com.st.dtit.cam.authentication.bean.bo.UserAuthenticationBo;
import com.st.dtit.cam.authentication.model.Application;
import com.st.dtit.cam.authentication.model.ApplicationService;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import com.st.dtit.cam.authentication.model.Role;
import com.st.dtit.cam.authentication.model.UserRoles;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BoMapper {

        public static final UserAuthenticationBo mapToUserAuthenticationBo(final List<UserRoles> userRoles){
               UserAuthenticationBo authenticationBo = new UserAuthenticationBo();
               authenticationBo.setApplicationUser(mapToApplicationUserBo(userRoles.get(0).getApplicationUser()));
               authenticationBo.setRole(userRoles.stream()
                                                 .map(usrRoles ->
                                                    mapToRoleBo(usrRoles.getRole()))
                                                   .collect(Collectors.toSet()));

               Application application = userRoles.get(0).getApplication();
               if (null != application && CollectionUtils.isNotEmpty(application.getServices())) {
                       authenticationBo.setServices(application.getServices()
                                                                     .stream()
                                                                     .map(BoMapper::mapToApplicationServiceBo)
                                                                     .collect(Collectors.toSet()));
               }
               return authenticationBo;
        }

        public static final ApplicationUserBo mapToApplicationUserBo(final ApplicationUser applicationUser){
                ApplicationUserBo applicationUserBo = new ApplicationUserBo();

                applicationUserBo.setUserId(applicationUser.getUserId());
                applicationUserBo.setUserName(applicationUser.getUserName());
                applicationUserBo.setFirstName(applicationUser.getFirstName());
                applicationUserBo.setLastName(applicationUser.getLastName());
                applicationUserBo.setMiddleName(applicationUser.getMiddleName());
                applicationUserBo.setEmailAddress(applicationUser.getEmailAddress());
                applicationUserBo.setActive(applicationUser.getActive());

                return applicationUserBo;
        }

        public static final RoleBo mapToRoleBo(final Role role){
                RoleBo  roleBo = new RoleBo();

                roleBo.setRoleId(role.getAppRoleId().getRoleId());
                roleBo.setRoleDescription(role.getRoleDescription());
                roleBo.setActive(role.getActive());

                return roleBo;
        }

        public static final ApplicationServiceBo mapToApplicationServiceBo(final ApplicationService applicationService) {
                ApplicationServiceBo applicationServiceBo = new ApplicationServiceBo();

                applicationServiceBo.setServiceCode(applicationService.getServiceCode());
                applicationServiceBo.setServiceName(applicationService.getServiceName());
                applicationServiceBo.setActive(applicationService.getActive());

                return applicationServiceBo;
        }

        private BoMapper(){}
}
