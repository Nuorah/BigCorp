package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.model.Site;

import java.io.IOException;

public interface SiteService {
    Site findById(String siteId);
    void readFile(String path) throws IOException;
}
