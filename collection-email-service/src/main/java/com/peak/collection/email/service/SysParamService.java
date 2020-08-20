package com.peak.collection.email.service;

import com.peak.collection.email.model.SysParamModel;

import java.util.List;

public interface SysParamService {
    List<SysParamModel> getParamList();

    SysParamModel getByUsername(String username);
}
