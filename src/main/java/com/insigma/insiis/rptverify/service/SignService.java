package com.insigma.insiis.rptverify.service;

import com.insigma.insiis.rptverify.Exception.BaseException;

/**
 * <p>签章/水印/二维码</p>
 * <p>pdf</p>
 *
 * @author 王森明
 * @date 2021/3/9 11:09
 * @since 1.0.0
 */
public interface SignService {
    String addQRCodeForPdf(String filePath, String sourceFilePath,String text, int width, int height,String charset) throws BaseException;
}
