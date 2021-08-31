package com.insigma.insiis.rptverify.service.imp;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.service.SignService;
import com.insigma.insiis.rptverify.util.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/9 11:11
 * @since 1.0.0
 */
@Service
public class SignServiceImpl  implements SignService {
    @Autowired
    private SignUtils signUtils;

    @Override
    public String addQRCodeForPdf(String filePath, String sourceFilePath,String text, int width, int height,String charset)  throws BaseException {
        signUtils.addQRCodeForPdf(filePath,sourceFilePath,text,width,height,charset);
        return filePath;
    }
}
