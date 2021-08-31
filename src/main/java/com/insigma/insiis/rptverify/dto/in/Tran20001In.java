package com.insigma.insiis.rptverify.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>文件查询验证</p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/11 14:04
 * @since 1.0.0
 */
@Data
@ApiModel("pdf证明校验")
@AllArgsConstructor
@NoArgsConstructor
public class Tran20001In {
    @ApiModelProperty(name="areaCode",notes = "统筹区编码",required = true)
    private String areaCode;

    @ApiModelProperty(name="tranNo",notes = "交易编号",required = true)
    private String tranNo;

    @ApiModelProperty(name="callerName",notes = "调用这名称",required = true)
    private String callerName;

    @ApiModelProperty(name="callerCode",notes = "调用这编码",required = true)
    private String callerCode;

    @ApiModelProperty(name="accessResource",notes = "访问来源" ,required = true)
    private String accessResource;

    @NotBlank(message = "文件ID不能为空")
    @ApiModelProperty(name="fileID",notes = "文件ID" ,required = true)
    private String fileID;
}
