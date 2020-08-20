package com.peak.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class EmailModel implements Serializable {

    private Long id;
    // 主题
    private String subject;
    // 内容
    private String content;
    // 附件
    private List<byte[]> attachmentList;
    // 收件人，多个用逗号(,)或分号(;)分割
    private String emailTo;

    private String emailType;

    private String cc;

    private String status;

    private Date createDate;

    private Date lastUpdateDate;

    private Date sendTime;

    private int retry;

    private String allowFrom;

}
