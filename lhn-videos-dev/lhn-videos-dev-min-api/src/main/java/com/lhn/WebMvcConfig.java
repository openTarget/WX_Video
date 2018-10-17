/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: WebMvcConfig
 * Author:   lhn
 * Date:     2018/9/17 21:37
 * Description: Spring boot mvc 配置类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lhn;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Spring boot mvc 配置类〉
 *
 * @author lhn
 * @create 2018/9/17
 * @since 1.0.0
 */
@Configuration // 配置文件 WebMvcConfigurerAdapter
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:D:/imooc_voides_images/");
    }
}