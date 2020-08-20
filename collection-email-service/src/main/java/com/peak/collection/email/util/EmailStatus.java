package com.peak.collection.email.util;

public enum EmailStatus {
    NEW("新建"),
    PROCESSING("正在处理"),
    SUCCESS("发送成功"),
    FAIL("发送失败")
    ;
    private String desc;

    EmailStatus(String desc) {
        this.desc = desc;
    }
}
