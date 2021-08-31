package com.insigma.insiis.rptverify.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * <p>二维码</p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/5 13:46
 * @since 1.0.0
 */
@Slf4j
public class QrCodeUtil {
    public static BufferedImage createQrCodeBufferdImage(String text, int width, int height,String charset) throws BaseException {
        try {
            Hashtable hints= new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, StringUtils.isBlank(charset)?"UTF-8":charset);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text,BarcodeFormat.QR_CODE, width, height, hints);
            return toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            log.info(ApiInfoEnum.QRCODE_CREATE.getMsg()+":{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.QRCODE_CREATE.getMsg());
        }
    }


    public static byte[] getImage(BufferedImage bufferedImage, String format){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }


}
