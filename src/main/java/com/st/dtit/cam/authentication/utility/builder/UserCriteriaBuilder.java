package com.st.dtit.cam.authentication.utility.builder;


import com.st.dtit.cam.authentication.bean.param.UserQueryParameter;
import com.st.dtit.cam.authentication.enums.TableProperty;

import java.util.HashMap;
import java.util.Map;

public class UserCriteriaBuilder extends BaseCriteriaBuilder{

        private String userId;

        private String[] userIds;

        private String userName;

        private String[] userNames;

        private String lastName;

        private String[] lastNames;

        private String firstName;

        private String[] firstNames;

        private String emailAddress;

        private String[] emailAddresses;

        private Boolean active;

        public UserCriteriaBuilder withQueryParameter(final UserQueryParameter parameter) {
               if (null != parameter && null != parameter.getFilterCriteria()) {
                     this.userId = setStringValue(parameter.getFilterCriteria().getUserId());
                     this.userName    = setStringValue(parameter.getFilterCriteria().getUserName());
                     this.lastName   = setStringValue(parameter.getFilterCriteria().getLastName());
                     this.firstName  = setStringValue(parameter.getFilterCriteria().getFirstName());
                     this.emailAddress = setStringValue(parameter.getFilterCriteria().getEmailAddress());

                     this.userIds = setStringArrayValue(parameter.getFilterCriteria().getUserIds());
                     this.userNames    = setStringArrayValue(parameter.getFilterCriteria().getUserNames());
                     this.lastNames   = setStringArrayValue(parameter.getFilterCriteria().getLastNames());
                     this.firstNames  = setStringArrayValue(parameter.getFilterCriteria().getFirstNames());
                     this.emailAddresses = setStringArrayValue(parameter.getFilterCriteria().getEmailAddresses());

                     if (parameter.getFilterCriteria().getActive() != null) {
                         this.active = parameter.getFilterCriteria().getActive();
                     }
               }
               return this;
        }

        public Map<TableProperty, Object> build() {
               final Map<TableProperty, Object> filterCriteriaMap = new HashMap<>();

               mapStringProperty(filterCriteriaMap, TableProperty.USER_ID, this.userId);
               mapStringProperty(filterCriteriaMap, TableProperty.USER_USERNAME, this.userName);
               mapStringProperty(filterCriteriaMap, TableProperty.LAST_NAME, this.lastName);
               mapStringProperty(filterCriteriaMap, TableProperty.FIRST_NAME, this.firstName);
               mapStringProperty(filterCriteriaMap, TableProperty.EMAIL_ADDRESS, this.emailAddress);

               mapStringArrayProperty(filterCriteriaMap, TableProperty.USER_ID, this.userIds);
               mapStringArrayProperty(filterCriteriaMap, TableProperty.USER_USERNAME, this.userNames);
               mapStringArrayProperty(filterCriteriaMap, TableProperty.LAST_NAME, this.lastNames);
               mapStringArrayProperty(filterCriteriaMap, TableProperty.FIRST_NAME, this.firstNames);
               mapStringArrayProperty(filterCriteriaMap, TableProperty.EMAIL_ADDRESS, this.emailAddresses);

               mapBooleanProperty(filterCriteriaMap, TableProperty.STATUS_ACTIVE, this.active);

               return filterCriteriaMap;
        }

}
