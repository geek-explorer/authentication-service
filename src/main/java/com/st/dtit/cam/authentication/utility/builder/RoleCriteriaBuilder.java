package com.st.dtit.cam.authentication.utility.builder;

import com.st.dtit.cam.authentication.bean.param.RoleQueryParameter;
import com.st.dtit.cam.authentication.enums.TableProperty;

import java.util.HashMap;
import java.util.Map;

public class RoleCriteriaBuilder extends BaseCriteriaBuilder{

        private String roleId;

        private String[] roleIds;

        private String applicationCode;

        private String[] applicationCodes;

        private String roleDescription;

        private String[] roleDescriptions;

        public RoleCriteriaBuilder withQueryParameter(final RoleQueryParameter parameter) {
               if (null != parameter &&  null != parameter.getFilterCriteria()) {
                   this.roleId = setStringValue(parameter.getFilterCriteria().getRoleId());
                   this.applicationCode = setStringValue(parameter.getFilterCriteria().getApplicationCode());
                   this.roleDescription = setStringValue(parameter.getFilterCriteria().getRoleDescription());

                   this.roleIds = setStringArrayValue(parameter.getFilterCriteria().getRoleIds());
                   this.applicationCodes = setStringArrayValue(parameter.getFilterCriteria().getApplicationCodes());
                   this.roleDescriptions = setStringArrayValue(parameter.getFilterCriteria().getApplicationCodes());
               }
               return this;
        }

        public Map<TableProperty, Object> build() {
               final Map<TableProperty, Object> filterCriteriaMap = new HashMap<>();

               mapStringProperty(filterCriteriaMap, TableProperty.ROLE_ID, this.roleId);
               mapStringProperty(filterCriteriaMap, TableProperty.RL_APPLICATION, this.applicationCode);
               mapStringProperty(filterCriteriaMap, TableProperty.ROLE_DESCRIPTION, this.roleDescription);

               mapStringArrayProperty(filterCriteriaMap, TableProperty.ROLE_ID, this.roleIds);
               mapStringArrayProperty(filterCriteriaMap, TableProperty.RL_APPLICATION, this.applicationCodes);
               mapStringArrayProperty(filterCriteriaMap, TableProperty.ROLE_DESCRIPTION, this.roleDescriptions);

               return filterCriteriaMap;
        }
}
