package com.insigma.insiis.rptverify.util;


import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import com.insigma.insiis.rptverify.config.SftpProperties;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/8 10:25
 * @since 1.0.0
 */
@Slf4j
@Component
public class SFTPUtil {
    // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

    @Autowired
    private SftpProperties config;

    /**
     * 创建SFTP连接
     *
     * @return
     * @throws Exception
     */
    public ChannelSftp createSftp() throws BaseException {
        try {
            JSch jsch = new JSch();
            log.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHost() + "], use password[" + config.getPassword() + "]");

            Session session = createSession(jsch, config.getHost(), config.getUsername(), config.getPort());
            session.setPassword(config.getPassword());
            session.connect(config.getSessionConnectTimeout());

            log.info("Session connected to {}.", config.getHost());

            Channel channel = session.openChannel(config.getProtocol());
            channel.connect(config.getChannelConnectedTimeout());

            log.info("Channel created to {}.", config.getHost());
            return (ChannelSftp) channel;
        } catch (Exception e) {
            log.info(ApiInfoEnum.SFTP_CHANNEL.getMsg() + ":{}", e.getMessage(), e);
            throw new BaseException(ApiInfoEnum.SFTP_CHANNEL.getMsg());
        }
    }


    /**
     * 加密秘钥方式登陆
     *
     * @return
     */
    public ChannelSftp connectByKey() throws BaseException {
        try {
            JSch jsch = new JSch();
            // 设置密钥和密码 ,支持密钥的方式登陆
            if (StringUtils.isNotBlank(config.getPrivateKey())) {
                if (StringUtils.isNotBlank(config.getPassphrase())) {
                    // 设置带口令的密钥
                    jsch.addIdentity(config.getPrivateKey(), config.getPassphrase());
                } else {
                    // 设置不带口令的密钥
                    jsch.addIdentity(config.getPrivateKey());
                }
            }
            log.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHost() + "], use private key[" + config.getPrivateKey()
                    + "] with passphrase[" + config.getPassphrase() + "]");

            Session session = createSession(jsch, config.getHost(), config.getUsername(), config.getPort());
            // 设置登陆超时时间
            session.connect(config.getSessionConnectTimeout());
            log.info("Session connected to " + config.getHost() + ".");

            // 创建sftp通信通道
            Channel channel = session.openChannel(config.getProtocol());
            channel.connect(config.getChannelConnectedTimeout());
            log.info("Channel created to " + config.getHost() + ".");
            return (ChannelSftp) channel;
        } catch (Exception e) {
            log.info(ApiInfoEnum.SFTP_CHANNEL.getMsg() + ":{}", e.getMessage(), e);
            throw new BaseException(ApiInfoEnum.SFTP_CHANNEL.getMsg());
        }

    }


    /**
     * 创建session
     *
     * @param jsch
     * @param host
     * @param username
     * @param port
     * @return
     * @throws Exception
     */
    public Session createSession(JSch jsch, String host, String username, Integer port) throws BaseException {
        try {
            Session session = null;
            if (port <= 0) {
                session = jsch.getSession(username, host);
            } else {
                session = jsch.getSession(username, host, port);
            }
            if (session == null) {
                throw new Exception(host + " session is null");
            }
            session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, config.getSessionStrictHostKeyChecking());
            return session;
        } catch (Exception e) {
            log.info(ApiInfoEnum.SFTP_SESSION.getMsg() + ":{}", e.getMessage(), e);
            throw new BaseException(ApiInfoEnum.SFTP_SESSION.getMsg());
        }

    }

    /**
     * 关闭连接
     *
     * @param sftp
     */
    public void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.info("sftp is closed already");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (Exception e) {
            //关闭连接异常，不抛出
            log.info(ApiInfoEnum.SFTP_DISCONNECT.getMsg() + ":{}", e.getMessage(), e);
        }
    }


    /**
     * 创建多级目录
     *
     * @param dirPath
     * @param sftp
     * @return
     */
    public void createDirs(String dirPath, ChannelSftp sftp) throws BaseException {
        if (StringUtils.isNoneBlank(dirPath) && sftp != null) {
            try{
                sftp.cd("/");
                log.info("Change directory {}", "/");
                String[] dirs = Arrays.stream(dirPath.split("/")).filter(StringUtils::isNotBlank).toArray(String[]::new);
                for (String item : dirs) {
                    try {
                        sftp.cd(item);
                        log.info("Change directory {}", item);
                    } catch (SftpException e) {
                        log.info("Create directory {}", item);
                        try {
                            sftp.mkdir(item);
                            sftp.cd(item);
                        } catch (SftpException e1) {
                            log.error("Create directory failure, directory:{}--->{}", item, e1.getMessage(), e1);
                            throw new BaseException(ApiInfoEnum.SFTP_MKDIR.getMsg());
                        }
                    }
                }
            }catch (SftpException re){
                dirPath=dirPath.replaceFirst(config.getRoot(),"");
                String[] dirs = Arrays.stream(dirPath.split("/")).filter(StringUtils::isNotBlank).toArray(String[]::new);
                for (String item : dirs) {
                    try {
                        sftp.cd(item);
                        log.info("Change directory {}", item);
                    } catch (SftpException e) {
                        log.info("Create directory {}", item);
                        try {
                            sftp.mkdir(item);
                            sftp.cd(item);
                        } catch (SftpException e1) {
                            log.error("Create directory failure, directory:{}--->{}", item, e1.getMessage(), e1);
                            throw new BaseException(ApiInfoEnum.SFTP_MKDIR.getMsg());
                        }
                    }
                }
            }

        }
    }

    /**
     * sftp单个文件上传
     * @param directory  文件所在目录
     * @param fileName  包含后缀的文件名
     * @param inputStream
     * @return
     * @throws BaseException
     */
    public void upload(String directory,String fileName, InputStream inputStream) throws BaseException {
        ChannelSftp sftp = null;
        try {
            long time=System.currentTimeMillis();
            sftp = this.createSftp();
            log.info(">>>>>>>>>>创建sftp连接耗时：{}毫秒",System.currentTimeMillis()-time);
            createDirs(directory, sftp);
            log.info(">>>>>>>>>>创建sftp连接到路径创建完成耗时：{}毫秒",System.currentTimeMillis()-time);
            sftp.put(inputStream, fileName);
            log.info(">>>>>>>>>>创建sftp连接到路径创建到文件上传完成耗时：{}毫秒",System.currentTimeMillis()-time);
        } catch (Exception e) {
            if(e instanceof BaseException){
                throw (BaseException)e;
            }
            log.error(ApiInfoEnum.SFTP_UPLOAD.getMsg()+"TargetPath: {}--->{}", directory, e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SFTP_UPLOAD.getMsg());
        } finally {
            this.disconnect(sftp);
        }
    }





    /**
     * sftp单个文件上传
     * @param directory 文件所在目录
     * @param fileName 包含后缀的文件名
     * @param file  文件
     * @throws BaseException
     */
    public void upload(String directory,String fileName, File file) throws BaseException {
        try{
            upload(directory,fileName,new FileInputStream(file));
        }catch (FileNotFoundException e){
            log.info(ApiInfoEnum.CLASS_NOTFOUND.getMsg()+",File:{}--->{}",file.getName(),e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.CLASS_NOTFOUND.getMsg()+",File:{}",file.getName());
        }
    }

    /**
     * sftp单个文件上传
     * @param directory 文件所在目录
     * @param fileName  包含后缀的文件名
     * @param bytes 二进制数组
     * @throws BaseException
     */
    public void upload(String directory,String fileName,byte[] bytes) throws BaseException {
        upload(directory,fileName,new ByteArrayInputStream(bytes));
    }

    /**
     * sftp单个文件上传
     * @param directory 文件所在目录(sftp 用户目录下的主路径)
     * @param fileName 包含后缀的文件名
     * @param sourceFile  文件全路径
     * @throws BaseException
     */
    public void upload(String directory,String fileName, String sourceFile) throws BaseException {
        upload(directory,fileName,new File(sourceFile));
    }

    /**
     * 将字符串按照指定的字符编码上传到sftp
     * @param directory 上传到sftp目录
     * @param fileName 文件在sftp端的命名
     * @param dataStr 待上传的数据
     * @param charsetName sftp上的文件，按该字符编码保存
     * @throws UnsupportedEncodingException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String fileName, String dataStr, String charsetName) throws BaseException {
        try{
            upload(directory, fileName,dataStr.getBytes(charsetName));
        }catch (UnsupportedEncodingException e){
            log.info(ApiInfoEnum.CLASS_NOTFOUND.getMsg()+":{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.CLASS_NOTFOUND.getMsg());
        }
    }

    /**
     * sftp 单个文件下载
     * @param sourceDir  sftp 文件所在目录
     * @param sourceFile sftp  文件名（包含后缀）
     * @param saveFile  需要保存的文件全路径
     * @throws BaseException
     */
    public void download(String sourceDir, String sourceFile, String saveFile) throws BaseException {
        ChannelSftp sftp =null;
        OutputStream outputStream = null;
        try {
            sftp= this.createSftp();
            if(StringUtils.isNotBlank(sourceDir)){
                sftp.cd(sourceDir);
                log.info("Change sourceDir to {}", sourceDir);
                outputStream = new FileOutputStream(new File(saveFile));
                sftp.get(sourceFile, outputStream);
                log.info("Download file success. sourceFile: {}", sourceDir+File.separator+sourceFile);
            }
        } catch (Exception e) {
            log.error(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+", sourceFile: {}--->{}", sourceDir+File.separator+sourceFile, e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+",sourceFile:{}",sourceDir+File.separator+sourceFile);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            disconnect(sftp);
        }
    }




    /**
     * 下载文件
     * @param sourceDir  sftp 文件所在目录
     * @param sourceFile sftp  文件名（包含后缀）
     * @return 字节数组
     * @throws BaseException
     */
    public byte[] download(String sourceDir, String sourceFile) throws BaseException{
        ChannelSftp sftp =null;
        OutputStream outputStream = null;
        try {
            sftp = this.createSftp();
            if(StringUtils.isNotBlank(sourceDir)){
                sftp.cd(sourceDir);
                log.info("Change sourceDir to {}", sourceDir);
                InputStream inputStream=sftp.get(sourceFile);
                byte[] fileData = IOUtils.toByteArray(inputStream);
                log.info("file:{} is download successful" , sourceDir+File.separator+sourceFile);
                return fileData;
            }
            return null;
        } catch (Exception e) {
            log.error(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+", sourceFile: {}--->{}", sourceDir+File.separator+sourceFile, e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+",sourceFile:{}",sourceDir+File.separator+sourceFile);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            disconnect(sftp);
        }
    }

    /**
     * 下载文件
     * @param sourceDir  sftp 文件所在目录
     * @param sourceFile sftp  文件名（包含后缀）
     * @return 字节数组
     * @throws BaseException
     */
    public InputStream downloadInputStream(String sourceDir, String sourceFile) throws BaseException{
        ChannelSftp sftp =null;
        try {
            sftp = this.createSftp();
            if(StringUtils.isNotBlank(sourceDir)){
                sftp.cd(sourceDir);
                log.info("Change sourceDir to {}", sourceDir);
                return sftp.get(sourceFile);
            }
            return null;
        } catch (Exception e) {
            log.error(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+", sourceFile: {}--->{}", sourceDir+File.separator+sourceFile, e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+",sourceFile:{}",sourceDir+File.separator+sourceFile);
        } finally {
            disconnect(sftp);
        }
    }

    /**
     * 下载文件内容为list
     * @param sourceDir  sftp 文件所在目录
     * @param sourceFile sftp  文件名（包含后缀）
     * @param charset
     * @return
     * @throws BaseException
     */
    public List<?> downloadContent2List(String sourceDir, String sourceFile,String charset) throws BaseException {
        ChannelSftp sftp =null;
        try {
            sftp = this.createSftp();
            if(StringUtils.isNotBlank(sourceDir)){
                sftp.cd(sourceDir);
                log.info("Change sourceDir to {}", sourceDir);
                InputStream inputStream =sftp.get(sourceFile);
                return IOUtils.readLines(inputStream,charset);
            }
            return null;
        } catch (Exception e) {
            log.error(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+", sourceFile: {}--->{}", sourceDir+File.separator+sourceFile, e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+",sourceFile:{}",sourceDir+File.separator+sourceFile);
        } finally {
            disconnect(sftp);
        }
    }


    /**
     * 删除文件
     * @param sourceDir  sftp 文件所在目录
     * @param sourceFile sftp  文件名（包含后缀）
     * @throws BaseException
     */
    public void deleteFile(String sourceDir,String sourceFile) throws BaseException {
        ChannelSftp sftp =null;
        try {
            if(StringUtils.isNotBlank(sourceDir)){
                sftp = this.createSftp();
                sftp.cd(sourceDir);
                log.info("Change sourceDir to {}", sourceDir);
                sftp.rm(sourceFile);
            }
        } catch (Exception e) {
            log.error(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+", sourceFile: {}--->{}", sourceDir+File.separator+sourceFile, e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SFTP_DOWNLOAD.getMsg()+",sourceFile:{}",sourceDir+File.separator+sourceFile);
        } finally {
            disconnect(sftp);
        }
    }
}
