package com.st.dtit.cam.authentication.bean.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFilterCriteria implements Serializable {

          private static final long serialVersionUID = 1503786636124524385L;

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
}
