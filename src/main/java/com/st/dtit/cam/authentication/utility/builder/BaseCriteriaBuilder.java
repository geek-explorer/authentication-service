package com.st.dtit.cam.authentication.utility.builder;

import com.st.dtit.cam.authentication.enums.TableProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseCriteriaBuilder implements Serializable {

        protected String setStringValue(String objectProperty){
               if (StringUtils.isNotEmpty(objectProperty)) {
                   return objectProperty;
               }
               return StringUtils.EMPTY;
        }

        protected String[] setStringArrayValue(String[] objectProperties) {
               if (null != objectProperties && objectProperties.length > 0) {
                   return objectProperties;
               }
               return new String[]{};
        }

        protected void mapStringProperty(Map<TableProperty, Object> filterCriteriaMap, TableProperty tableProperty, final String criteria) {
            if (StringUtils.isNotEmpty(criteria)) {
                filterCriteriaMap.put(tableProperty, criteria);
            }
        }

        protected void mapLongProperty(Map<TableProperty, Object> filterCriteriaMap, TableProperty tableProperty, final Long criteria){
            if (null != criteria) {
                filterCriteriaMap.put(tableProperty, criteria);
            }
        }

        protected void mapBooleanProperty(Map<TableProperty, Object> filterCriteriaMap, TableProperty tableProperty, final Boolean criteria) {
            if (null != criteria) {
                 filterCriteriaMap.put(tableProperty, criteria);
            }
        }

        protected void mapStringArrayProperty(Map<TableProperty, Object> filterCriteriaMap, TableProperty tableProperty, final String[] criteria) {
            if (null != criteria && criteria.length > 0) {
                filterCriteriaMap.put(tableProperty, criteria);
            }
        }

        protected void mapLongArrayProperty(Map<TableProperty, Object> filterCriteriaMap, TableProperty tableProperty, final Long[] criteria) {
            if (null != criteria && criteria.length > 0) {
                filterCriteriaMap.put(tableProperty, criteria);
            }
        }

}
