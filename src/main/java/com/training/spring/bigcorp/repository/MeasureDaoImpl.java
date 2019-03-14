package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Measure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class MeasureDaoImpl implements MeasureDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Measure measure) {
        em.persist(measure);
    }

    @Override
    public Measure findById(Long id) {
        return em.find(Measure.class, id);
    }

    @Override
    public List<Measure> findAll() {
        return em.createQuery("select m from Measure m",
                Measure.class)
                .getResultList();
    }

    @Override
    public void delete(Measure measure) {
        em.remove(measure);
    }
}
