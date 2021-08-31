package com.insigma.insiis.rptverify.filter;

import com.alibaba.fastjson.JSON;
import com.insigma.insiis.rptverify.comm.ApiInfoEnum;
import com.insigma.insiis.rptverify.comm.DataRtn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * <p></p>
 * <p></p>
 * @author 王森明
 * @date 2021/3/25 11:31
 * @since 1.0.0
 */
 @Slf4j
public class InjectFilter implements Filter {

    private String badstr;
    private String ignoreUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.badstr=filterConfig.getInitParameter("badstr");
        this.ignoreUrl=filterConfig.getInitParameter("ignoreUrl");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        try{
            if(StringUtils.isNotBlank(ignoreUrl)){
                if(Arrays.stream(ignoreUrl.split(",")).anyMatch(item->request.getRequestURI().equals(item))){
                    log.info("忽略路径：{}",request.getRequestURI());
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
            }
            boolean bFlag=false;
            Enumeration params = request.getParameterNames();
            if(params!=null){
                String sqlStr="";
                while (params.hasMoreElements()) {
                    String [] values=request.getParameterValues(params.nextElement().toString());
                    sqlStr=Arrays.stream(values).map(item->item.toString()).reduce("",String::concat);
                    if(sqlValidate(sqlStr)){
                        bFlag=true;
                        break;
                    }
                }
                if (bFlag) {
                    log.info("【{}】{}:{}",ApiInfoEnum.SECURITY_CHECK_INJECT.getCode(),ApiInfoEnum.SECURITY_CHECK_INJECT.getMsg(),sqlStr);
                    response.getWriter().write(JSON.toJSONString(new DataRtn<>(ApiInfoEnum.SECURITY_CHECK_INJECT.getCode())));
                }else{
                    filterChain.doFilter(servletRequest,servletResponse);
                }
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }

        }catch (Exception e){
            log.info("【{}】{}：{}",ApiInfoEnum.SECURITY_CHECK_FAILURE.getCode(),ApiInfoEnum.SECURITY_CHECK_FAILURE.getMsg(),e.getMessage(),e);
            response.getWriter().write(JSON.toJSONString(new DataRtn("【"+ApiInfoEnum.SECURITY_CHECK_FAILURE.getCode()+"】:"+e.getMessage())));
        }
    }

    @Override
    public void destroy() {
        this.badstr=null;
        this.ignoreUrl=null;
    }


    protected boolean sqlValidate(String str) {
        str = str.toLowerCase();
        String badStr = this.badstr;
        String[] badStrs = badStr.split("\\|");

        for(int i = 0; i < badStrs.length; ++i) {
            if (str.indexOf(badStrs[i]) >= 0) {
                log.warn("当前请求参数为：{}", str);
                log.warn("检测到非法字符串：{}", badStrs[i]);
                return true;
            }
        }

        return false;
    }

}
