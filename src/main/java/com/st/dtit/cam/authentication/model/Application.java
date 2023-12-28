package com.st.dtit.cam.authentication.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "AUTH_APPLICATIONS")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region="application")
public class Application implements Serializable {

      private static final long serialVersionUID = 3982243217830183153L;

      @Id
      @Column(name = "APP_CODE")
      private String applicationCode;

      @Column(name = "APP_NAME")
      private String applicationName;

      @Column(name = "ACTIVE")
      private Boolean active;

      @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
      @JoinTable(name = "AUTH_APPLICATION_SERVICES",
                 joinColumns = {@JoinColumn(name="APP_CODE")},
                 inverseJoinColumns = {@JoinColumn(name="SERVICE_CODE")})
      private Set<ApplicationService> services;

}
