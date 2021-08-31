package com.insigma.insiis.rptverify.dto.out;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/10 13:41
 * @since 1.0.0
 */
@Api("证书下载")
@Data
public class Tran10001Out{
    @ApiModelProperty(name="path",notes = "证书路径",required = true)
    private String path;
}
