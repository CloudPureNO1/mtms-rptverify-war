package com.insigma.insiis.rptverify.filter;

import com.alibaba.fastjson.JSON;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import com.insigma.insiis.rptverify.comm.DataRtn;
import com.insigma.insiis.rptverify.service.opdb.RptWhitelistService;
import com.insigma.insiis.rptverify.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/17 13:13
 * @since 1.0.0
 */
@Slf4j

public class WhiteListFilter implements Filter {
    private String ips;
    private String ignoreUrl;
    @Autowired
    private RptWhitelistService rptWhitelistService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>WhiteListFilter init");
         ips=filterConfig.getInitParameter("ips");
         ignoreUrl=filterConfig.getInitParameter("ignoreUrl");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
 
        
        try{

            log.info("白名单：{}",ips);
            String ip= CommonUtils.getIpAddr(request);
            log.info("当前的请求ip:{}，请求url:{}",ip,request.getRequestURI());
            if(passUrl(request.getRequestURI())){
                log.info("忽略路径：{}",request.getRequestURI());
                filterChain.doFilter(request, response);
            }else if(checkWhiteIps(ip,ips)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else if(rptWhitelistService.checkWhiteList(ip)) {
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                log.info(ApiInfoEnum.SECURITY_CHECK_NO_PASS.getMsg());
                response.getWriter().write(JSON.toJSONString(new DataRtn(ApiInfoEnum.SECURITY_CHECK_NO_PASS.getCode())));
            }
        }catch (Exception e) {
            log.info("【{}】{}：{}",ApiInfoEnum.SECURITY_CHECK_FAILURE.getCode(),ApiInfoEnum.SECURITY_CHECK_FAILURE.getMsg(),e.getMessage(),e);
            response.getWriter().write(JSON.toJSONString(new DataRtn("【"+ApiInfoEnum.SECURITY_CHECK_FAILURE.getCode()+"】:"+e.getMessage())));
        }
    }

    @Override
    public void destroy() {
        if(ips!=null){
            ips=null;
        }
    }

    public boolean passUrl(String url){
        if(StringUtils.isNotBlank(ignoreUrl)&& Arrays.stream(url.split(",")).anyMatch(item->ignoreUrl.equals(item))){
            return true;
        }
        return false;
    }

    public boolean checkWhiteIps(String ip, String ips){
        if(StringUtils.isNotBlank(ips)){
            return Arrays.stream(ips.split(",")).anyMatch(item->item.equals(ip));
        }
        return true;
    }


}
