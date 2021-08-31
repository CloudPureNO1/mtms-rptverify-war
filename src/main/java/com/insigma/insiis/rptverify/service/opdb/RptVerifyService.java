package com.insigma.insiis.rptverify.service.opdb;

import com.insigma.insiis.rptverify.model.RptVerify;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/9 14:09
 * @since 1.0.0
 */
public interface RptVerifyService {
    int add(RptVerify rptVerify);
    RptVerify findByID(String rptId);
}
