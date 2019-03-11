package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.config.Monitored;
import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.service.measure.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CaptorServiceImpl implements CaptorService {

    @Autowired
    private MeasureService fixedMeasureService;
    @Autowired
    private MeasureService simulatedMeasureService;
    @Autowired
    private MeasureService realMeasureService;

    public CaptorServiceImpl(){
        System.out.println("Init CaptorServiceImpl :" + this);
    }

    @Monitored
    @Override
    public Set<Captor> findBySite(String siteId) {
        //System.out.println("Appel de findBySite :" + this);
        Set<Captor> captors = new HashSet<>();
        if (siteId == null) {
            return captors;
        }
        captors.add(new Captor("Capteur A"));
        return captors;
    }

    @Override
    public Set<Measure> readMeasure(String siteId) {
        return null;
    }


}
