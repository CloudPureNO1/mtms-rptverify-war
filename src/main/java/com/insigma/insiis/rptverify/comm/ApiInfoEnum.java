package com.insigma.insiis.rptverify.comm;

/**
 * <p></p>
 * <p></p>
 * @author 王森明
 * @date 2021/3/3 17:02
 * @since 1.0.0
 */
public enum ApiInfoEnum {
    SUCCESS("0","成功"),
    FAILURE("-1" ,"失败"),
    SFTP_CHANNEL("SFTP0001" ,"创建SFTP连接异常"),
    SFTP_SESSION("SFTP0002" ,"创建SFTP会话异常"),
    SFTP_DISCONNECT("SFTP0003" ,"SFTP关闭异常"),
    SFTP_MKDIR("SFTP0004", "SFTP创建目录失败"),
    SFTP_UPLOAD("SFTP0005", "SFTP文件上传失败"),
    SFTP_DOWNLOAD("SFTP0005", "SFTP文件下载失败"),
    SIGN_PDF_QRCODE("SIGN-PDF-001", "PDF文件添加二维码失败"),
    QRCODE_CREATE("QRCODE-0001", "二维码生成失败"),
    SERVICE_EXC("EXC-53-10001", "统一外层服务转换异常"),
    SERVICE_EXC_BIZ("EXC-53-10002", "证书下载失败"),
    CLASS_NOTFOUND("EXC-10002", "没有找到对应类"),
    NO_SUCH_METHOD("EXC-10003", "没有找到对应方法"),
    INVOCATION_TARGET("EXC-10004", "反射执行方法发生异常"),
    ILLEGAL_ACCESS("EXC-10005", "反射方法等没有执行权限"),
    SECURITY_CHECK_NO_PASS("CHECK-10001", "白名单校验不通过"),
    SECURITY_CHECK_FAILURE("CHECK-10002", "安全校验发生异常"),
    SECURITY_CHECK_INJECT("CHECK-10003", "发送的请求中含有非法字符"),
    DB_SAVE_FAILURE("DB-10001", "数据存储失败"),
    FILE_TYPE_ERR("FILE-10001", "文件类型错误：请传入pdf 或 PDF 或者 不传入"),
    BB_TRAN_EXC("BB-10001", "报表转换失败"),
    BB_CLOSE_EXC("BB-10002", "输出流关闭异常"),
    FILE_TYPE("FILE-TYPE","pdf"),
    FILE_TYPE_C("FILE-TYPE_C",".cpt")
     ;

    ApiInfoEnum(String code ,String msg) {
        this.code=code;
        this.msg=msg;
    }
 
 

  
 
    private String code;
 
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

 

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
