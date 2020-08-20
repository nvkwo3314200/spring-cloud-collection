package com.peak.collection.email.mapper;

import com.peak.collection.email.model.SysParamModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysParamMapper {
    @Select(" select id,username,password,host,port,encoding,personal,status,protocol,other from sys_param where status = 10 ")
    List<SysParamModel> list();

    @Select(" select id,username,password,host,port,encoding,personal,status,protocol,other from sys_param where username = #{username} ")
    List<SysParamModel> listByName(String username);
}
