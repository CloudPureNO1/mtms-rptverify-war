package com.insigma.insiis.rptverify.util;

import com.fr.base.FRContext;
import com.fr.base.Parameter;
import com.fr.dav.LocalEnv;
import com.fr.general.ModuleContext;
import com.fr.io.TemplateWorkBookIO;
import com.fr.io.exporter.*;
import com.fr.main.impl.WorkBook;
import com.fr.report.module.EngineModule;
import com.fr.stable.StringUtils;
import com.fr.stable.WriteActor;
import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * <p>报表转换工具</p>
 * <p></p>
 * @author 王森明
 * @date 2021/3/3 11:57
 * @since 1.0.0
 */
@Slf4j
@Component
public class TransFormUtil {

    public String  transForm(String envpath, String templateName, Map<String, Object> paramMap, String outPatn, String outFileName, String outFileType,String callerCode,String accessResource,String templateArea,String rptVerifyDbType) throws BaseException {
        FRContext.setCurrentEnv(new LocalEnv(envpath));
        ModuleContext.startModule(EngineModule.class.getName());
        FileOutputStream outputStream = null;
        try {
            outFileType=StringUtils.isBlank(outFileType)?ApiInfoEnum.FILE_TYPE.getMsg():outFileType;
            if(!ApiInfoEnum.FILE_TYPE.getMsg().equalsIgnoreCase(outFileType)){
                throw new BaseException(ApiInfoEnum.FILE_TYPE_ERR.getMsg());
            }
            String uuid=UUID.randomUUID().toString().replaceAll("-","");
            String cptName=templateName+templateArea+rptVerifyDbType;
            WorkBook workbook = (WorkBook) TemplateWorkBookIO.readTemplateWorkBook(FRContext.getCurrentEnv(), cptName + ApiInfoEnum.FILE_TYPE_C.getMsg());
            resetParams(workbook, paramMap);
            String date=new SimpleDateFormat("yyyyMMdd").format(new Date());
            outPatn=outPatn+File.separator+templateName + File.separator+date;
            checkPath(outPatn);
            String outFilePath =  outPatn+File.separator+uuid+"."+ outFileType;
            outputStream = new FileOutputStream(new File(outFilePath));
            PDFExporter PdfExport = new PDFExporter();
            PdfExport.export(outputStream, workbook.execute(paramMap, new WriteActor()));
            return outFilePath;
        } catch (Exception e) {
            log.info("{}：{}", ApiInfoEnum.BB_TRAN_EXC.getMsg(),e.getMessage(), e);
            throw new BaseException(ApiInfoEnum.BB_TRAN_EXC.getMsg()+":"+e.getMessage());
        } finally {
            try {
                if(outputStream!=null){outputStream.close();}
            } catch (IOException e) {
                log.info("{}：{}",ApiInfoEnum.BB_CLOSE_EXC.getMsg(), e.getMessage(), e);
                throw new BaseException(ApiInfoEnum.BB_TRAN_EXC.getMsg()+":"+ApiInfoEnum.BB_CLOSE_EXC.getMsg());
            }
            ModuleContext.stopModules();
        }
    }

    private void resetParams(WorkBook workbook, Map<String, Object> paramMap) {
        Parameter[] parameters = workbook.getParameters();
        if (parameters != null && parameters.length > 0) {
            Arrays.stream(parameters).forEach(item -> {
                if (!paramMap.containsKey(item.getName())) {
                    paramMap.remove(item.getName());
                }
            });
        }
    }

    private void checkPath(String outPatn) {
        File fileParen = new File(outPatn);
        if (!fileParen.exists() && !fileParen.isFile()) {
            fileParen.mkdirs();
        }
    }

}
