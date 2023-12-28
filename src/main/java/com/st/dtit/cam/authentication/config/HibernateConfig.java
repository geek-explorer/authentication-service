package com.st.dtit.cam.authentication.config;

import java.io.Serializable;

public class HibernateConfig implements Serializable {

    private static final long serialVersionUID = -8405444004489683091L;

    private static HibernateConfig hibernateConfigInstance = null;

    public static HibernateConfig getInstance(){
        if (null == hibernateConfigInstance){
            hibernateConfigInstance = new HibernateConfig();
        }
        return hibernateConfigInstance;
    }

    private HibernateConfig(){}
}
