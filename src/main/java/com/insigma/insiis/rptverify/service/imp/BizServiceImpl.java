package com.insigma.insiis.rptverify.service.imp;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import com.insigma.insiis.rptverify.comm.DataRtn;
import com.insigma.insiis.rptverify.dto.in.Tran10001In;
import com.insigma.insiis.rptverify.dto.in.Tran20001In;
import com.insigma.insiis.rptverify.dto.out.Tran10001Out;
import com.insigma.insiis.rptverify.dto.out.Tran20001Out;
import com.insigma.insiis.rptverify.model.RptVerify;
import com.insigma.insiis.rptverify.service.BizService;
import com.insigma.insiis.rptverify.service.SFTPService;
import com.insigma.insiis.rptverify.service.SignService;
import com.insigma.insiis.rptverify.service.TransFormService;
import com.insigma.insiis.rptverify.service.opdb.RptVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/4 14:01
 * @since 1.0.0
 */
@Slf4j
@Service
public class BizServiceImpl implements BizService {
    @Value("${sftp.remotePath}")
    private String remotePath;

    @Value("${sftp.systemType}")
    private String systemType;

    @Value("${http.url.preffix}")
    private String httpUrlPrefix;

    @Value("${file.preffix}")
    private String filePrefix;

    @Value("${rpt.verify.db.type}")
    private String rptVerifyDbType;

    @Autowired
    private TransFormService transFormService;

    @Autowired
    private SFTPService sftpService;

    @Autowired
    private SignService signService;

    @Autowired
    private RptVerifyService rptVerifyService;

    /**
     * pdf 报表打印
     * @param tran10001In
     * @throws BaseException
     */
    @Override
    public DataRtn serve10001(Tran10001In tran10001In) throws BaseException {
        try{
            String sourceFilePath=transFormService.transForm(tran10001In.getTemplateName(),tran10001In.getTemplateParams(),tran10001In.getOutFileName(),tran10001In.getOutFileType(),tran10001In.getCallerCode(),tran10001In.getAccessResource(),tran10001In.getTemplateArea());
            int index=sourceFilePath.lastIndexOf(File.separator);
            String uuid=UUID.randomUUID().toString().replaceAll("-","");
            String filePath=sourceFilePath.substring(0,index)+File.separator+uuid+"."+tran10001In.getOutFileType();
            String text= UUID.randomUUID().toString().replaceAll("-","")+"-"+tran10001In.getCallerCode();
            signService.addQRCodeForPdf(filePath,sourceFilePath,text,62,62,"UTF-8");
            File file=new File(sourceFilePath);
            if(file.exists()){
                file.delete();
            }
            String fileSeparator="/";
            if("windows".equalsIgnoreCase(systemType)){
                fileSeparator="\\";
            }
            String date=new SimpleDateFormat("yyyyMMdd").format(new Date());
            String directory=tran10001In.getTemplateName()+fileSeparator+date;
            String fileName=uuid+"."+tran10001In.getOutFileType();
            long time=System.currentTimeMillis();
            sftpService.upload(remotePath+fileSeparator+directory,fileName,filePath);
            log.info("证书文件上传SFTP耗时：{}",System.currentTimeMillis()-time);
            String rsPath=remotePath+fileSeparator+directory+fileSeparator+fileName;
            RptVerify rptVerify=new RptVerify();
            rptVerify.setRptId(text);
            rptVerify.setRptName(tran10001In.getTemplateName()+tran10001In.getTemplateArea()+rptVerifyDbType);
            rptVerify.setRptPath(rsPath);
            rptVerify.setAccessSource(tran10001In.getAccessResource());
            rptVerify.setRptType(tran10001In.getRptType());
            rptVerify.setRptDesc("二维码报表pdf");
            rptVerify.setRptTime(new Date());
            rptVerify.setCallerCode(tran10001In.getCallerCode());
            rptVerify.setCallerName(tran10001In.getCallerName());
            rptVerify.setTemplateArea(tran10001In.getTemplateArea());
            rptVerifyService.add(rptVerify);
            Tran10001Out tran10001Out=new Tran10001Out();
            int i=rsPath.indexOf(filePrefix);
            if(i==0){
                rsPath=httpUrlPrefix+rsPath.substring(filePrefix.length());
            }
            tran10001Out.setPath(rsPath);
            return new DataRtn (tran10001Out);
        }catch (Exception e){
            if(e instanceof SQLException){
                log.info("{},{}:{}",ApiInfoEnum.SERVICE_EXC_BIZ.getMsg(),ApiInfoEnum.DB_SAVE_FAILURE.getMsg(),e.getMessage(),e);
                throw new BaseException(ApiInfoEnum.SERVICE_EXC_BIZ.getMsg()+":"+ApiInfoEnum.DB_SAVE_FAILURE.getMsg());
            }
            log.info(ApiInfoEnum.SERVICE_EXC_BIZ.getMsg()+":{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SERVICE_EXC_BIZ.getMsg()+":"+e.getMessage());
        }
    }



    @Override
    public DataRtn serve20001(@Valid Tran20001In tran20001In) throws BaseException {
        try{
            RptVerify rptVerify = rptVerifyService.findByID(tran20001In.getFileID());
            if(rptVerify==null|| StringUtils.isBlank(rptVerify.getRptPath())){
                log.info("没有对应的文件信息");
                throw new BaseException("没有对应的文件信息");
            }
            String rsPath=rptVerify.getRptPath();
            int i=rsPath.indexOf(filePrefix);
            if(i==0){
                rsPath=httpUrlPrefix+rsPath.substring(filePrefix.length());
            }
            return new DataRtn (new Tran20001Out(rsPath));
        }catch (Exception e){
            if(e instanceof SQLException){
                log.info("{},{}:{}",ApiInfoEnum.SERVICE_EXC_BIZ.getMsg(),ApiInfoEnum.DB_SAVE_FAILURE.getMsg(),e.getMessage(),e);
                throw new BaseException(ApiInfoEnum.SERVICE_EXC_BIZ.getMsg()+":"+ApiInfoEnum.DB_SAVE_FAILURE.getMsg());
            }
            log.info(ApiInfoEnum.SERVICE_EXC_BIZ.getMsg()+":{}",e.getMessage(),e);
            throw new BaseException(ApiInfoEnum.SERVICE_EXC_BIZ.getMsg()+":"+e.getMessage());
        }
    }
}
