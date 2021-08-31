package com.insigma.insiis.rptverify.service;

import com.insigma.insiis.rptverify.Exception.BaseException;

import java.io.File;
import java.io.InputStream;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/8 10:58
 * @since 1.0.0
 */
public interface SFTPService {

    void upload(String directory,String fileName, InputStream inputStream)  throws BaseException;

    void upload(String directory,String fileName, File file) throws BaseException;

    void upload(String directory,String fileName,byte[] bytes) throws BaseException;

    String upload(String directory,String fileName, String sourceFile) throws BaseException;

    void download(String sourceDir, String sourceFile, String saveFile) throws BaseException;

    byte[] download(String sourceDir, String sourceFile) throws BaseException;

    InputStream downloadInputStream(String sourceDir, String sourceFile) throws BaseException;
}
