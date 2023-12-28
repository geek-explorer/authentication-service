package com.st.dtit.cam.authentication.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

import static com.st.dtit.cam.authentication.enums.DataTypeEnum.OBJECT;
import static com.st.dtit.cam.authentication.enums.DataTypeEnum.STRING;


public enum TableProperty {


    EMAIL_ADDRESS("email-address", "emailAddress", "Email Address", STRING),

    FIRST_NAME("first-name", "firstName", "First Name", STRING),
    LAST_NAME("last-name", "lastName", "Last Name", STRING),
    MIDDLE_NAME("middle-name", "middleName", "Middle Name", STRING),

    RL_APPLICATION("rl-application", "application.applicationCode", "Application Code", STRING),
    ROLE_ID("rold-id", "appRoleId", "Role Id", STRING),
    ROLE_DESCRIPTION("role-description", "roleDescription", "Role Description", STRING),

    STATUS_ACTIVE("status-active", "active", "Active", STRING),

    TOKEN_ID("token-id", "token", "Authentication Token", STRING),

    UR_USER("ur-user", "applicationUser", "Application User"),
    UR_APPLICATION("ur-application", "application.applicationCode", "Application Code", STRING),
    UR_USERNAME("ur-user-name", "applicationUser.userName", "Application User Name", STRING),
    USER_ID("user-id", "userId", "User Id", STRING),
    USER_USERNAME("username", "userName", "User Name", STRING);



    private String key;

    private String field;

    private String description;

    private DataTypeEnum dataType;

    TableProperty(final String key, final String field, final String description){
            this.key            = key;
            this.field          = field;
            this.description    = description;
            this.dataType       = OBJECT;
    }

    TableProperty(final String key, final String field, final String description, final DataTypeEnum dataType){
            this.key            = key;
            this.field          = field;
            this.description    = description;
            this.dataType       = dataType;
    }

    public String getKey(){return this.key;}

    public String getField(){return this.field;}

    public String getDescription(){return this.description;}

    public DataTypeEnum getDataType() {return this.dataType;}


    public static Optional<TableProperty> getTableProperty(final String key){
            if (StringUtils.isNotEmpty(key)){
                return Arrays.stream(values())
                       .filter(tableProperty -> tableProperty.getKey().equalsIgnoreCase(key))
                       .findAny();
            }
            return Optional.empty();
    }
}
