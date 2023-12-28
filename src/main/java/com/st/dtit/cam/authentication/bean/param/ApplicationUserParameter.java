package com.st.dtit.cam.authentication.bean.param;

import com.st.dtit.cam.authentication.bean.bo.ApplicationUserBo;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationUserParameter implements Serializable {

        private ApplicationUserBo authenticationUser;

}
