package com.insigma.insiis.rptverify.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>不同类型的下载提供不同的接口</p>
 *
 * @author 王森明
 * @date 2021/3/3 14:57
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("pdf证明下载")
public class Tran10001In extends TranBaseIn{
    @NotBlank(message = "访问来源不能为空")
    @ApiModelProperty(name="accessResource",notes = "访问来源" ,required = true)
    private String accessResource;

    @NotBlank(message = "类型不能为空")
    @ApiModelProperty(name="rptType",notes = "类型: 0-普通打印,1-二维码证明下载，2-水印证明下载，3-签章证明下载",required = true)
    private String rptType;

    @NotBlank(message = "调用者名称不能为空")
    @ApiModelProperty(name="callerName",notes = "调用这名称",required = true)
    private String callerName;

    @NotBlank(message = "调用者编码不能为空")
    @ApiModelProperty(name="callerCode",notes = "调用这编码",required = true)
    private String callerCode;
}
