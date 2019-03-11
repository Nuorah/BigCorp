package com.training.spring.bigcorp.config;

import com.training.spring.bigcorp.model.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Set;

@Configuration
@PropertySource("classpath:application.properties")
public class BigCorpApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ApplicationInfo applicationInfo() {
        String name = environment.getRequiredProperty("bigcorp.name");
        Integer version = environment.getRequiredProperty("bigcorp.version", Integer.class);
        Set<String> emails = environment.getRequiredProperty("bigcorp.emails", Set.class);
        String webSiteUrl = environment.getRequiredProperty("bigcorp.webSiteUrl");

        return new ApplicationInfo(name, version, emails, webSiteUrl);
    }
}