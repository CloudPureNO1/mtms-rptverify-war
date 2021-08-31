package com.insigma.insiis.rptverify.comm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/9 9:20
 * @since 1.0.0
 */
@NoArgsConstructor
@Data
public class TestBean implements Serializable {
    /**
     * accessResource :
     * rptType :
     * callerName :
     * callerCode :
     * areaCode :
     * tranNo :
     * envpath :
     * outPatn :
     * templateName :
     * templateParams : {}
     * outFileName :
     * outFileType :
     */

    private String accessResource;
    private String rptType;
    private String callerName;
    private String callerCode;
    private String areaCode;
    private String tranNo;
    private String envpath;
    private String outPatn;
    private String templateName;
    private TemplateParamsBean templateParams;
    private String outFileName;
    private String outFileType;

    @NoArgsConstructor
    @Data
    public static class TemplateParamsBean implements Serializable {
    }
}
