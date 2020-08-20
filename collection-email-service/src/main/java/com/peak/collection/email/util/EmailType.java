package com.peak.collection.email.util;

public enum EmailType {

    NORMAL("普通"),
    IMPORTANT("重要"),
    ;

    private String desc;

    private EmailType(String desc) {
        this.desc = desc;
    }
}
