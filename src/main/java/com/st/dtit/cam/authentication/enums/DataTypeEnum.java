package com.st.dtit.cam.authentication.enums;

public enum DataTypeEnum {

        BOOLEAN ("Boolean"),
        INTEGER("Integer"),
        OBJECT("Object"),
        STRING("String"),
        TIMESTAMP("TimeStamp");

    private String dataType;

    DataTypeEnum(String dataType){
        this.dataType = dataType;
    }

    public String getDataType(){
          return this.getDataType();
    }
}
