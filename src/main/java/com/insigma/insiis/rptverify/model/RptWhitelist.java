package com.insigma.insiis.rptverify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/15 10:02
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RptWhitelist implements java.io.Serializable{
    private String whiteId;//WHITE_ID	N	VARCHAR2(32)	N			uuid 主键
    private String whiteName;//WHITE_NAME	N	VARCHAR2(50)	N			英文名称
    private String whiteCnname;//WHITE_CNNAME	N	VARCHAR2(100)	N			中文名称
    private String whiteIp;//WHITE_IP	N	VARCHAR2(50)	N			ip地址
    private String whiteFlag;//WHITE_FLAG	N	VARCHAR2(1)	N			是否有效：1有效 0无效
    private Date createTime;//CREATE_TIME	N	DATE	Y			创建时间
    private String whiteType;//WHITE_TYPE	N	VARCHAR2(1)	N			类型：1-下载和校验，2 -校验，3下载
    private String whiteDesc;//WHITE_DESC	N	VARCHAR2(500)	Y			描述
}
