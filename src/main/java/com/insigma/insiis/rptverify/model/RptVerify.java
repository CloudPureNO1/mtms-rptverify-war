package com.insigma.insiis.rptverify.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RptVerify implements Serializable {

    private String rptId;

    private String rptName;

    private String rptPath;

    private String rptDesc;

    private Date rptTime;

    private String accessSource;


    private String rptType;
    private String callerName;
    private String callerCode;

    private String templateArea;
    private static final long serialVersionUID = 1L;


}