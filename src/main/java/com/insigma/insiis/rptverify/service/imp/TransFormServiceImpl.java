package com.insigma.insiis.rptverify.service.imp;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.service.TransFormService;
import com.insigma.insiis.rptverify.util.PathUtils;
import com.insigma.insiis.rptverify.util.TransFormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 14:11
 * @since 1.0.0
 */
@Service
public class TransFormServiceImpl implements TransFormService {
    @Value("${report.verify.envpath}")
    private String envpath;
    @Value("${report.verify.outPath}")
    private String outPath;

    @Value("${rpt.verify.db.type}")
    private String rptVerifyDbType;

    @Autowired
    private TransFormUtil transFormUtil;

    @Autowired
    private PathUtils pathUtils;

    @Override
    public String transForm(String templateName, Map<String, Object> paramMap, String outFileName, String outFileType, String callerCode, String accessResource,String templateArea) throws BaseException {
        String path=pathUtils.getClassLResource("");
        int index=path.lastIndexOf("classes");
        path=path.substring(0,index);
        return  transFormUtil.transForm(path,templateName,paramMap,outPath,outFileName,outFileType,callerCode,accessResource,templateArea,rptVerifyDbType);
    }
}
