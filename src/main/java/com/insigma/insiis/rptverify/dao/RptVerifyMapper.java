package com.insigma.insiis.rptverify.dao;

import com.insigma.insiis.rptverify.model.RptVerify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RptVerifyMapper {

    int deleteByPrimaryKey(String rptId);

    /**
     * 主键自增
     * @param record
     * @return
     */
   /* int insert(RptVerify record);*/

    /**
     * 主键使用java UUID相关生成
     * @param record
     * @return
     */
    int insertByJavaUUID(RptVerify record);

    RptVerify selectByPrimaryKey(String rptId);

    List<RptVerify> selectAll();

    int updateByPrimaryKey(RptVerify record);
}