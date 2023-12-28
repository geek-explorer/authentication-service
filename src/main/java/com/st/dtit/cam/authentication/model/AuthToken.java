package com.st.dtit.cam.authentication.model;

import com.st.dtit.cam.authentication.model.composite.AuthTokenId;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "AUTH_TOKEN")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="auth_token")
public class AuthToken implements Serializable {

        private static final long serialVersionUID = -902964271291922193L;

        @EmbeddedId
        private AuthTokenId authTokenId;

        @Column(name = "TOKEN")
        private String token;

        @Column(name = "EXPIRY_DATE")
        private Instant expiryDate;

}
