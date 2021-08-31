package com.insigma.insiis.rptverify.comm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 16:49
 * @since 1.0.0
 */
@ApiModel("统一返回数据")
@Data
public class DataRtn<T> {
    @ApiModelProperty(name="code",notes="状态 0 成功，其他失败",required = true)
    private String code;
    @ApiModelProperty(name="msg",notes="返回信息",required = true)
    private String msg;
    @ApiModelProperty(name="data",notes="返回数据",required = false)
    private T data;
    @ApiModelProperty(name="timestamp",notes="时间",required = true)
    private String timestamp;

    public DataRtn(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public DataRtn(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public DataRtn(T data) {
        this.code=ApiInfoEnum.SUCCESS.getCode();
        this.msg=ApiInfoEnum.SUCCESS.getMsg();
        this.data = data;
        this.timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public DataRtn(String msg) {
        this.code= ApiInfoEnum.FAILURE.getCode();
        this.msg = msg;
        this.timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public DataRtn() {
    }
}
