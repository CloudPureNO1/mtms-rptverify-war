package com.insigma.insiis.rptverify.service.imp;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.config.SftpProperties;
import com.insigma.insiis.rptverify.service.SFTPService;
import com.insigma.insiis.rptverify.util.SFTPUtil;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/8 11:48
 * @since 1.0.0
 */
@Slf4j
@Service("sftpService")
public class SFTPServiceImpl implements SFTPService {

    @Autowired
    private SFTPUtil sftpUtil;


    @Override
    public void upload(String directory, String fileName, InputStream inputStream) throws BaseException {
        sftpUtil.upload(directory,fileName,inputStream);
    }

    @Override
    public void upload(String directory, String fileName, File file) throws BaseException {
        sftpUtil.upload(directory,fileName,file);
    }

    @Override
    public void upload(String directory, String fileName, byte[] bytes) throws BaseException {
        sftpUtil.upload(directory,fileName,bytes);
    }

    @Override
    public String upload(String directory, String fileName, String sourceFile) throws BaseException {
        sftpUtil.upload(directory,fileName,sourceFile);
        return directory+"/"+fileName;
    }

    @Override
    public void download(String sourceDir, String sourceFile, String saveFile) throws BaseException {
        sftpUtil.download(sourceDir,sourceFile,saveFile);
    }

    @Override
    public byte[] download(String sourceDir, String sourceFile) throws BaseException {
        return sftpUtil.download(sourceDir,sourceFile);
    }

    @Override
    public InputStream downloadInputStream(String sourceDir, String sourceFile) throws BaseException{
        return sftpUtil.downloadInputStream(sourceDir,sourceFile);
    }
}
