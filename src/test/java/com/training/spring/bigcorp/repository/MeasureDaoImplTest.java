package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.Site;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ContextConfiguration(classes = {DaoTestConfig.class})
public class MeasureDaoImplTest {

    @Autowired
    private MeasureDao measureDao;
    private Site site;
    private Captor captor;

    @Before
    public void init() {
        site = new Site("name");
        site.setId("site1");
        captor = new Captor("name", site);
        captor.setId("c1");
    }

    @Test
    public void findById() {
        Measure measure = measureDao.findById("1");
        Assertions.assertThat(measure.getId()).isEqualTo(1L);
        Assertions.assertThat(measure.getInstant()).isEqualTo(Instant.parse("2018-08-09T11:00:00.000Z"));
        Assertions.assertThat(measure.getValueInWatt()).isEqualTo(1_000_000);
        Assertions.assertThat(measure.getCaptor().getName()).isEqualTo("Eolienne");
        Assertions.assertThat(measure.getCaptor().getSite().getName()).isEqualTo("Bigcorp Lyon");
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Measure measure = measureDao.findById("0");
        Assertions.assertThat(measure).isNull();
    }

    @Test
    public void findAll() {
        List<Measure> measures = measureDao.findAll();
        Assertions.assertThat(measures)
                .hasSize(10)
                .extracting(Measure::getCaptor)
                .extracting("id", "name")
                .contains(Tuple.tuple("c1", "Eolienne"))
                .contains(Tuple.tuple("c2", "Laminoire à chaud"));
    }

    @Test
    public void create() {
        Assertions.assertThat(measureDao.findAll()).hasSize(10);
        measureDao.create(new Measure(Instant.now(), 1, captor));
        Assertions.assertThat(measureDao.findAll())
                .hasSize(11)
                .extracting(Measure::getValueInWatt)
                .contains(1);
    }

    @Test
    public void update() {
        Measure measure = measureDao.findById("1");
        Assertions.assertThat(measure.getValueInWatt()).isEqualTo(1_000_000);
        measure.setValueInWatt(2_333_666);
        measureDao.update(measure);
        measure = measureDao.findById("1");
        Assertions.assertThat(measure.getValueInWatt()).isEqualTo(2_333_666);
    }

    @Test
    public void deleteById() {
        Assertions.assertThat(measureDao.findAll()).hasSize(10);
        measureDao.deleteById("1");
        Assertions.assertThat(measureDao.findAll()).hasSize(9);
    }

}