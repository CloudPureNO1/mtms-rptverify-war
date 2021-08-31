package com.insigma.insiis.rptverify.config;

import com.insigma.insiis.rptverify.filter.WhiteListFilter;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/17 13:19
 * @since 1.0.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "white.list",ignoreUnknownFields = false)
public class WhiteListFilterConfig {

    private String ips;

    private String ignoreUrl;

    private List<String> urlPatterns=new ArrayList<String>();


    private Map<String,String> initParameters=new HashMap<String,String>();


    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }


    @Bean
    public WhiteListFilter registerWhitelistFilter(){
        return new WhiteListFilter();
    }

    @Bean
    public FilterRegistrationBean filterFilterRegistration() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(registerWhitelistFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        initParameters.put("ips",ips);
        initParameters.put("ignoreUrl",ignoreUrl);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
