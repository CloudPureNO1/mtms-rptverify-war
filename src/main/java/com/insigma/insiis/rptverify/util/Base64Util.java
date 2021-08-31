package com.insigma.insiis.rptverify.util;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 14:33
 * @since 1.0.0
 */
public class Base64Util {
    public static String decode(String jsonStr) {
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(jsonStr);
            return new String(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String encode(String str) throws UnsupportedEncodingException {
        return new BASE64Encoder().encode(str.getBytes("utf-8"));
    }
    public static String encode(byte [] bytes)  {
        return new BASE64Encoder().encode(bytes);
    }


    public static InputStream getInputStream(String base64Str){
        byte[] bytes = Base64.getDecoder().decode(base64Str.split(",")[1]);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStream;
    }
}
