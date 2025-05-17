package com.charity_hub.shared.infrastructure;

import feign.RequestInterceptor;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;

@Configuration
@EnableFeignClients(basePackages = "com.charity_hub.cases.shared")
public class FeignConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignConfig.class);
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String authorization = attributes.getRequest().getHeader("Authorization");
                if (authorization != null) {
                    log.debug("Forwarding Authorization header: {}", authorization);
                    requestTemplate.header("Authorization", authorization);
                } else {
                    log.warn("No Authorization header found in request");
                }
            } else {
                log.warn("No request attributes found");
            }
        };
    }
} 
