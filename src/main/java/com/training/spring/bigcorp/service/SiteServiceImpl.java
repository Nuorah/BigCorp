package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.config.Monitored;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.repository.SiteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service
public class SiteServiceImpl implements SiteService {

    private final static Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private SiteDao dao;

    private CaptorService captorService;

    public SiteServiceImpl(CaptorService captorService){
        logger.debug("Init SiteServiceImpl :" + this);
        this.captorService = captorService;
    }

    @Monitored
    @Override
    public Site findById(String siteId) {
        logger.debug("Appel de findById :" + this);
        if (siteId == null) {
            return null;
        }

        Site site = dao.findById(siteId);
        return site;
    }


    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
