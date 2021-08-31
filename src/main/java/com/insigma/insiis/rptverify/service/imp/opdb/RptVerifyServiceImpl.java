package com.insigma.insiis.rptverify.service.imp.opdb;

import com.insigma.insiis.rptverify.dao.RptVerifyMapper;
import com.insigma.insiis.rptverify.model.RptVerify;
import com.insigma.insiis.rptverify.service.opdb.RptVerifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/9 14:13
 * @since 1.0.0
 */
@Service
public class RptVerifyServiceImpl implements RptVerifyService {
    @Resource
    private RptVerifyMapper rptVerifyMapper;

    @Override
    public int add(RptVerify rptVerify) {
        return rptVerifyMapper.insertByJavaUUID(rptVerify);
    }

    @Override
    public RptVerify findByID(String rptId) {
        return rptVerifyMapper.selectByPrimaryKey(rptId);
    }
}
