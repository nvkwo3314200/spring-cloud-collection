package com.peak.collection.email.service.impl;

import com.peak.collection.email.mapper.SysParamMapper;
import com.peak.collection.email.model.SysParamModel;
import com.peak.collection.email.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysParamServiceImpl implements SysParamService {

    @Autowired
    private SysParamMapper sysParamMapper;

    @Override
    public List<SysParamModel> getParamList() {
        return sysParamMapper.list();
    }

    @Override
    public SysParamModel getByUsername(String username) {
        List<SysParamModel> list = sysParamMapper.listByName(username);
        if(list.size() > 0) return list.get(0);
        return null;
    }
}
