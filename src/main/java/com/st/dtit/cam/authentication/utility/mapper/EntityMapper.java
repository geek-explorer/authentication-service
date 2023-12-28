package com.st.dtit.cam.authentication.utility.mapper;


import com.st.dtit.cam.authentication.bean.bo.ApplicationUserBo;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import org.apache.commons.lang3.StringUtils;

import static com.st.dtit.cam.authentication.constant.CommonConstant.VALUE_EMPTY_FIELD;
import static com.st.dtit.cam.authentication.constant.CommonConstant.VALUE_REMOVE_VALUE;


public class EntityMapper {

         public static ApplicationUser mapToApplicationUserEntity(final ApplicationUser  applicationUser, final ApplicationUserBo applicationUserBo) {
                applicationUser.setUserId(applicationUserBo.getUserId());
                applicationUser.setUserName(setStringValues(applicationUser.getUserName(), applicationUserBo.getUserName()));
                applicationUser.setFirstName(setStringValues(applicationUser.getFirstName(), applicationUserBo.getFirstName()));
                applicationUser.setLastName(setStringValues(applicationUser.getLastName(), applicationUserBo.getLastName()));
                applicationUser.setEmailAddress(setStringValues(applicationUser.getEmailAddress(), applicationUserBo.getEmailAddress()));
                if (null != applicationUserBo.getActive()) {
                       applicationUser.setActive(applicationUserBo.getActive());
                }
                return applicationUser;
         }

         private static String setStringValues(String strOriginalValue, String strFieldValue) {
                 if (StringUtils.isNotEmpty(strFieldValue)) {
                      if (VALUE_REMOVE_VALUE.equalsIgnoreCase(strFieldValue)){
                          return VALUE_EMPTY_FIELD;
                      }
                      return strFieldValue;
                 }
                 return strOriginalValue;
         }

}
