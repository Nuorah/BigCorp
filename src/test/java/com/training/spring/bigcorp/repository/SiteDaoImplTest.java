package com.training.spring.bigcorp.repository;


import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.PowerSource;
import com.training.spring.bigcorp.model.Site;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
public class SiteDaoImplTest {

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private EntityManager em;

    @Before
    public void init(){
    }

    @Test
    public void findById() {
        Site site = siteDao.findById("site1");
        Assertions.assertThat(site.getId()).isEqualTo("site1");
        Assertions.assertThat(site.getName()).isEqualTo("Bigcorp Lyon");
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Site site = siteDao.findById("unknown");
        Assertions.assertThat(site).isNull();
    }

    @Test
    public void findAll() {
        List<Site> sites = siteDao.findAll();
        Assertions.assertThat(sites).hasSize(1);
    }

    @Test
    public void create() {
        Assertions.assertThat(siteDao.findAll()).hasSize(1);
        Site site = new Site("New site");
        siteDao.persist(site);

        Assertions.assertThat(siteDao.findAll())
                .hasSize(2)
                .extracting(Site::getName)
                .contains("Bigcorp Lyon", "New site");
    }

    @Test
    public void update() {
        Site site = siteDao.findById("site1");
        Assertions.assertThat(site.getName()).isEqualTo("Bigcorp Lyon");
        site.setName("New name");
        siteDao.persist(site);
        site = siteDao.findById("site1");
        Assertions.assertThat(site.getName()).isEqualTo("New name");
    }


    @Test
    public void deleteById() {
        Site site = new Site("New Site");
        site.setId("site2");
        em.persist(site);
        Assertions.assertThat(siteDao.findAll()).hasSize(2);
        siteDao.delete(siteDao.findById("site2"));
        Assertions.assertThat(siteDao.findAll()).hasSize(1);
    }

    @Test
    public void deleteByIdShouldThrowExceptionWhenIdIsUsedAsForeignKey() {
        Site site = siteDao.findById("site1");
        Assertions
                .assertThatThrownBy(() -> {
                    siteDao.delete(site);
                    em.flush();
                })
                .isExactlyInstanceOf(PersistenceException.class)
                .hasCauseExactlyInstanceOf(ConstraintViolationException.class);
    }
}