package com.emily.mapper;

import com.emily.pojo.WebApp;
import com.emily.pojo.WebAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WebAppMapper {
    long countByExample(WebAppExample example);

    int deleteByExample(WebAppExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WebApp record);

    int insertSelective(WebApp record);

    List<WebApp> selectByExample(WebAppExample example);

    WebApp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WebApp record, @Param("example") WebAppExample example);

    int updateByExample(@Param("record") WebApp record, @Param("example") WebAppExample example);

    int updateByPrimaryKeySelective(WebApp record);

    int updateByPrimaryKey(WebApp record);
}