package com.peak.collection.email.model;

import lombok.Data;

@Data
public class SysParamModel {
    private Long id;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String protocol;
    private String encoding;
    private String personal;
    private String other;
}
