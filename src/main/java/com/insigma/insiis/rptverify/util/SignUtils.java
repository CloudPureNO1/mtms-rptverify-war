package com.insigma.insiis.rptverify.util;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * <p>签章/水印/二维码</p>
 * <p>pdf</p>
 *
 * @author 王森明
 * @date 2021/3/5 14:49
 * @since 1.0.0
 */
@Slf4j
@Component
public class SignUtils {

    public BufferedImage createQrCodeImg(String text, int width, int height,String charset) throws BaseException {
        BufferedImage bufferedImage = QrCodeUtil.createQrCodeBufferdImage(text, width, height, charset);
        return bufferedImage;
    }

    public void addQRCodeForPdf(String filePath, String sourceFilePath, String text, int width, int height,String charset) throws BaseException {
        BufferedImage bufferedImage = createQrCodeImg(text, width, height,charset);
        setQRCodeForPDF(filePath, sourceFilePath, bufferedImage,width,height);
    }

    public void setQRCodeForPDF(String filePath, String sourceFilePath, BufferedImage bufferedImage,int width,int height) throws BaseException {
        PdfReader reader=null;
        PdfStamper stamper=null;
        BufferedOutputStream bos = null;
        try{
            checkAndMkdirs(filePath);
            bos=new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            reader = new PdfReader(new FileInputStream(new File(sourceFilePath)));
            stamper= new PdfStamper(reader, bos);
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte waterMar=null;

            PdfGState gs = new PdfGState();
            Rectangle pageSizeWithRotation = null;
            for (int i = 1; i < total; i++) {
                waterMar = stamper.getOverContent(i);
                waterMar.beginText();
                waterMar.setGState(gs);
                Image itextimage = Image.getInstance(QrCodeUtil.getImage(bufferedImage, "png"));
                pageSizeWithRotation = reader.getPageSizeWithRotation(i);
                float pageHeight = pageSizeWithRotation.getHeight();
                itextimage.setAbsolutePosition(50, pageHeight-height-20);
                itextimage.scaleToFit(width, height);
                itextimage.scaleAbsolute(width, height);
                waterMar.addImage(itextimage);
                waterMar.endText();
                waterMar.stroke();
            }
        }catch (Exception e){
            log.info(ApiInfoEnum.SIGN_PDF_QRCODE.getMsg()+":{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SIGN_PDF_QRCODE.getMsg());
        }finally {
            if(stamper!=null){
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader!=null){
                reader.close();
            }
        }

    }

    public void checkAndMkdirs(String path){
        File file=new File(path.substring(0,path.lastIndexOf(File.separator)));
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
