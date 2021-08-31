package com.insigma.insiis.rptverify.dto.out;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/10 13:41
 * @since 1.0.0
 */
@Api("证书校验")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tran20001Out {
    @ApiModelProperty(name="path",notes = "证书路径",required = true)
    private String path;
}
