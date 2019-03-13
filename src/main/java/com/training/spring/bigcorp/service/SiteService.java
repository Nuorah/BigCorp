package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public interface SiteService {



    Site findById(String siteId);
}
