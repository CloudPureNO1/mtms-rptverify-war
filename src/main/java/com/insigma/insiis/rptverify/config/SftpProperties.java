package com.insigma.insiis.rptverify.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/8 10:54
 * @since 1.0.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "sftp")
public class SftpProperties {
    private String systemType;
    private String host;
    private Integer port;
    private String protocol;
    private String username;
    private String password;
    private String root;
    private String privateKey;
    private String passphrase;
    private String sessionStrictHostKeyChecking;
    private Integer sessionConnectTimeout;
    private Integer channelConnectedTimeout;
    private String remotePath;
    private String localPath;
}
