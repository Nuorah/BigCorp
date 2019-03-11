package com.training.spring.bigcorp.model;

import java.util.Set;

public class ApplicationInfo {

    private String name;
    private Integer version;
    private Set<String> emails;
    private String webSiteUrl;

    public ApplicationInfo(String name, Integer version, Set<String> emails, String webSiteUrl) {
        this.name = name;
        this.version = version;
        this.emails = emails;
        this.webSiteUrl = webSiteUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getVersion() {
        return version;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }
}