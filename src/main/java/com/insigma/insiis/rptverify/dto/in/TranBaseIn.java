package com.insigma.insiis.rptverify.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 14:57
 * @since 1.0.0
 */
@Data
@ApiModel("公共参数")
public class TranBaseIn implements java.io.Serializable{
    //公共参数

    @NotBlank(message = "统筹区编码不能为空")
    @ApiModelProperty(name="areaCode",notes = "统筹区编码",required = true)
    private String areaCode;

    @NotBlank(message = "交易编号不能为空")
    @ApiModelProperty(name="tranNo",notes = "交易编号",required = true)
    private String tranNo;

    //报表公共参数  不需传入

    @ApiModelProperty(name="envpath",notes = "运行环境路径,系统预留，无须传入",required = false)
    private String envpath;
    @ApiModelProperty(name="outPatn",notes = "导出证明存储的主目录，系统预留，无须传入",required = false)
    private String outPatn;

    //报表公共参数 必须传入

    @NotBlank(message = "模板所属地区不能为空")
    @ApiModelProperty(name="templateArea",notes = "模板所属地区11-北京市,12-天津市,13-河北省,14-山西省,15-内蒙古自治区,21-辽宁省,22-吉林省,23-黑龙江省,31-上海市,32-江苏省,33-浙江省,34-安徽省,35-福建省,36-江西省,37-山东省,41-河南省,42-湖北省,43-湖南省,44-广东省,45-广西壮族自治区,46-海南省,50-重庆市,51-四川省,52-贵州省,53-云南省,54-西藏自治区,61-陕西省,62-甘肃省,63-青海省,64-宁夏回族自治区,65-新疆维吾尔自治区,71-台湾省,81-香港特别行政区,82-澳门特别行政区",required = true)
    private String templateArea;

    @NotBlank(message = "模板名不能为空")
    @ApiModelProperty(name="templateName",notes = "模板名（英文）",required = true)
    private String templateName;

    @NotNull(message = "模板名中使用到的参数键值对对象不能为空")
    @ApiModelProperty(name="templateParams",notes = "模板名中使用到的参数键值对对象",required = true)
    private Map<String, Object> templateParams=new HashMap<String,Object>();

    @ApiModelProperty(name="outFileName",notes = "输出的文件名（不含后缀）",required = false)
    private String outFileName="file";

    @ApiModelProperty(name="outFileType",notes = "输出的文件类型,pdf,PDF ，不传默认为pdf",required = false)
    private String outFileType="pdf";


}
