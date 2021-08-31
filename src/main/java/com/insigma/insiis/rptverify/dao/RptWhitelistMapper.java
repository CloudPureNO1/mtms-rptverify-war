package com.insigma.insiis.rptverify.dao;

import com.insigma.insiis.rptverify.model.RptVerify;
import com.insigma.insiis.rptverify.model.RptWhitelist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RptWhitelistMapper {

    int deleteByPrimaryKey(String whiteId);


    /**
     * 主键使用java UUID相关生成
     * @param record
     * @return
     */
    int insertByJavaUUID(RptWhitelist record);

    RptWhitelist selectByPrimaryKey(String rptId);

    List<RptWhitelist> selectAll(@Param("whiteFlag")String whiteFlag);

}