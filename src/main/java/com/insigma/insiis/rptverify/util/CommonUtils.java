package com.insigma.insiis.rptverify.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/19 10:38
 * @since 1.0.0
 */
public class CommonUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static String getSystemType(){
        String osName=System.getProperty("os.name");
        if(StringUtils.isNotBlank(osName)){
            if(osName.toLowerCase().indexOf("linux")!=-1){
                return "linux";
            }
            if(osName.toLowerCase().indexOf("windows")!=-1){
                return "windows";
            }
            if(osName.toLowerCase().indexOf("mac")!=-1){
                return "mac";
            }
            return "others";
        }
        return "linux";
    }

    public static void main(String [] args){
        System.out.println(getSystemType());
    }
}
