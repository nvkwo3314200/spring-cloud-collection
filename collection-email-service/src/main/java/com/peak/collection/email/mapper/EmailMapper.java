package com.peak.collection.email.mapper;

import com.peak.common.model.EmailModel;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface EmailMapper {

    @Insert("INSERT INTO `email_notify` (`email_type`, `subject`, `content`, `email_to`, `cc`, `status`, `create_date`, `last_update_date`, `retry`, `allow_from`) " +
            "VALUES (#{emailType}, #{subject}, #{content}, #{emailTo}, #{cc}, #{status}, #{createDate}, #{lastUpdateDate}, #{retry}, #{allowFrom})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int add(EmailModel emailModel);

    @Update("UPDATE email_notify SET subject=#{subject},content=#{content},email_to=#{emailTo}, cc=#{cc}, status=#{status},last_update_date=#{lastUpdateDate}, retry=#{retry}, allow_from = #{allowFrom} WHERE id=#{id}")
    int update(EmailModel emailModel);

    @Delete("DELETE FROM email_notify WHERE id=#{id}")
    int delete(Long id);

    @Select({"<script>",
            "SELECT id, email_type emailType, subject, content, email_to emailTo, cc, status, create_date createDate, last_update_date lastUpdateDate, retry, allow_from from email_notify",
            "WHERE 1=1",
            "and retry &lt; 3",
            "<when test='id != null'>",
            "and id = #{id}",
            "</when>",
            "<when test='emailType != null'>",
            "and email_type = #{emailType}",
            "</when>",
            "<when test='status != null'>",
            "and status = #{status}",
            "</when>",
            "</script>"})
    List<EmailModel> list(EmailModel emailModel);

    @Update("update email_notify set status = 'PROCESSING' where status in ('NEW', 'FAIL') and id = #{id}")
    int processing(Long id);

}
