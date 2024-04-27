package com.ruoyi.web.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态页面不缓存
        registry.addResourceHandler("/*.html")
                .addResourceLocations("/public/", "classpath:/static/")
                .setCachePeriod(0);
    }

}
