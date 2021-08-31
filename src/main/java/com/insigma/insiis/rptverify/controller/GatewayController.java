package com.insigma.insiis.rptverify.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.DataRtn;
import com.insigma.insiis.rptverify.service.GatewayService;
import com.insigma.insiis.rptverify.service.TransFormService;
import com.insigma.insiis.rptverify.util.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 14:28
 * @since 1.0.0
 */
@Api("证明打印下载")
@Slf4j
@Validated
@Controller
@RequestMapping("/api")
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    @ApiOperation("统一接入方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tranNo",value="交易编号",required = true,paramType = "path",dataType = "字符串"),
            @ApiImplicitParam(name="jsonStr",value="交易参数体",required = true,paramType = "body",dataType = "json"),
    })
    @PostMapping("/gateway/{tranNo}")
    @ResponseBody
    public DataRtn gateway(@PathVariable(value="tranNo",required = true) String tranNo, @RequestBody(required = false) @NotBlank(message = "传入参数不能为空") String jsonStr) throws BaseException {
        log.info("【{}】传入参数：{}",tranNo,jsonStr);
        return (DataRtn)gatewayService.server(tranNo,jsonStr);
    }


    @ApiOperation("统一接入验证方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tranNo",value="交易编号",required = true,paramType = "path",dataType = "字符串"),
            @ApiImplicitParam(name="jsonStr",value="交易参数体",required = true,paramType = "body",dataType = "json"),
    })
    @PostMapping("/gatewayVerify/{tranNo}")
    @ResponseBody
    public DataRtn gatewayVerify(@PathVariable(value="tranNo",required = true) String tranNo, @RequestBody(required = false) @NotBlank(message = "传入参数不能为空") String jsonStr) throws BaseException {
        log.info("【{}】传入参数：{}",tranNo,jsonStr);
        return (DataRtn)gatewayService.server(tranNo,jsonStr);
    }


    @ApiOperation("统一接入方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tranNo",value="交易编号",required = true,paramType = "path",dataType = "字符串"),
            @ApiImplicitParam(name="jsonStr",value="交易参数体",required = true,paramType = "body",dataType = "json编码后的字符串"),
    })
    @PostMapping("/gatewayEncoded/{tranNo}")
    @ResponseBody
    public DataRtn gatewayEncoded(@PathVariable(value="tranNo",required = true) String tranNo, @RequestBody(required = false) @NotBlank(message = "传入参数不能为空") String jsonStr) throws BaseException {
        log.info("【{}】传入参数：{}",tranNo,jsonStr);
        jsonStr=Base64Util.decode(jsonStr);
        log.info("【{}】传入参数：{}",tranNo,jsonStr);
        return (DataRtn)gatewayService.server(tranNo,jsonStr);
    }
 

}
