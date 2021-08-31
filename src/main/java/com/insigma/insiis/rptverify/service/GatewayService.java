package com.insigma.insiis.rptverify.service;

import com.insigma.insiis.rptverify.Exception.BaseException;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/4 13:19
 * @since 1.0.0
 */
public interface GatewayService {
    Object server(String tranNo,String jsonStr) throws BaseException;
}
