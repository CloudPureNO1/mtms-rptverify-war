package com.insigma.insiis.rptverify.service;

import com.insigma.insiis.rptverify.Exception.BaseException;

import java.util.Map;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 14:11
 * @since 1.0.0
 */
public interface TransFormService {
    String transForm(String templateName, Map<String, Object> paramMap,String outFileName, String outFileType,String callerCode,String accessResource,String templateArea) throws BaseException ;
}
