package com.insigma.insiis.rptverify.config;

import com.insigma.insiis.rptverify.filter.InjectFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/25 11:39
 * @since 1.0.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "inject.sql",ignoreInvalidFields = true)
public class InjectConfig {
    private String badstr;
    private String urlPatterns;
    private String ignoreUrl;
    private Map<String,Object> initParameters=new HashMap<>();

    @Bean
    public InjectFilter registerFilter(){
        return new InjectFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(registerFilter());
        filterRegistrationBean.addUrlPatterns(urlPatterns);
        filterRegistrationBean.setName("injectFilter");
        filterRegistrationBean.setOrder(2);
        initParameters.put("ignoreUrl",ignoreUrl);
        initParameters.put("badstr",badstr);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }


}
