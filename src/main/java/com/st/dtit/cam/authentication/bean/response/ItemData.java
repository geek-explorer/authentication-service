package com.st.dtit.cam.authentication.bean.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemData implements Serializable {

    private static final long serialVersionUID = -8194564871932535034L;

    private Integer total;

    private Object items;

}
