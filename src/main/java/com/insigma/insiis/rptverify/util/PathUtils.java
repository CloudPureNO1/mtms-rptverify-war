package com.insigma.insiis.rptverify.util;

import org.springframework.stereotype.Component;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/14 15:37
 * @since 1.0.0
 */
@Component
public class PathUtils {
    public String getClassLResource(String name){
        String url="";
        url=this.getClass().getClassLoader().getResource(name==null?"":name).getPath();
        return url;
    }
}
