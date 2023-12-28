package com.st.dtit.cam.authentication.enums;

public enum DataSourceEnum {

    DB_SOURCE_AUTH("AUTHENTICATION_DATABASE");

    private String dataSource;

    DataSourceEnum(String dataSource){
        this.dataSource = dataSource;
    }

    public String getDataSource(){
        return this.dataSource;
    }
}
