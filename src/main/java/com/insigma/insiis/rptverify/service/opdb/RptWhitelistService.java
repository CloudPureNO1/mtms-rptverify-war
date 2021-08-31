package com.insigma.insiis.rptverify.service.opdb;

import com.insigma.insiis.rptverify.model.RptVerify;
import com.insigma.insiis.rptverify.model.RptWhitelist;

import java.util.List;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/9 14:09
 * @since 1.0.0
 */
public interface RptWhitelistService {
    int add(RptWhitelist rptVerify);
    RptWhitelist findByID(String whiteId);
    List<RptWhitelist>findAll(String whiteFlag);
    boolean checkWhiteList(String ip);
}
