package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.config.Monitored;
import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.repository.CaptorDaoImpl;
import com.training.spring.bigcorp.repository.CrudDao;
import com.training.spring.bigcorp.service.measure.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CaptorServiceImpl implements CaptorService {

    @Autowired
    private MeasureService fixedMeasureService;
    @Autowired
    private MeasureService simulatedMeasureService;
    @Autowired
    private MeasureService realMeasureService;
    @Autowired
    private CaptorDao dao;

    public CaptorServiceImpl(){
        System.out.println("Init CaptorServiceImpl :" + this);
    }

    @Monitored
    @Override
    public Set<Captor> findBySite(String siteId) {
        return dao.findBySiteId(siteId)
                .stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Measure> readMeasure(String siteId) {
        return null;
    }


}
