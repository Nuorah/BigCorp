package com.training.spring.bigcorp;

import com.training.spring.bigcorp.config.properties.BigCorpApplicationProperties;
import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.repository.MeasureDao;
import com.training.spring.bigcorp.repository.SiteDao;
import com.training.spring.bigcorp.service.CaptorService;
import com.training.spring.bigcorp.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BigcorpApplication {

	private final static Logger logger = LoggerFactory.getLogger(BigcorpApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BigcorpApplication.class,
				args);
		BigCorpApplicationProperties bigCorpApplicationProperties = context.getBean(BigCorpApplicationProperties.class);
		logger.info("========================================================================");
		logger.info("Application [" + bigCorpApplicationProperties.getName() + "] - version: " + bigCorpApplicationProperties.getVersion());
		logger.info("plus d'informations sur " + bigCorpApplicationProperties.getWebSiteUrl());
		logger.info("========================================================================");
		//logger.info(context.getBean(CaptorDao.class).findAll().toString());
		logger.info(context.getBean(CaptorService.class).findBySite("site2").toString());
		logger.info(context.getBean(CaptorService.class).findBySite("site1").toString());
	}
}
