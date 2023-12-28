package com.st.dtit.cam.authentication.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "AUTH_USER_ROLES")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region="user_roles")
public class UserRoles implements Serializable {

       private static final long serialVersionUID = -7313490347238493557L;

       @Id
       @Column(name = "USER_ROLE_ID")
       private Long userRoleId;

       @OneToOne
       @JoinColumn(name = "USER_ID")
       private ApplicationUser applicationUser;

       @OneToOne
       @JoinColumn(name = "APP_CODE", insertable = false, updatable = false)
       private Application application;

       @OneToOne
       @JoinColumns(
          {
             @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false, referencedColumnName="ROLE_ID"),
             @JoinColumn(name = "APP_CODE", insertable = false, updatable = false, referencedColumnName="APP_CODE")
          }
       )
       private Role role;

}
