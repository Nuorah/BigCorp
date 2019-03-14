package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.FixedCaptor;
import com.training.spring.bigcorp.model.RealCaptor;
import com.training.spring.bigcorp.model.Site;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
@Transactional
public class CaptorDaoImplTest {

    @Autowired
    private CaptorDao captorDao;

    @Autowired
    private EntityManager em;

    private Site site;

    @Before
    public void init() {
        site = new Site("Bigcorp Lyon");
        site.setId("site1");
    }

    @Test
    public void findById() {
        Optional<Captor> captor = captorDao.findById("c1");
        Assertions.assertThat(captor.get().getId()).isEqualTo("c1");
        Assertions.assertThat(captor.get().getName()).isEqualTo("Eolienne");
        Assertions.assertThat(captor.get().getSite().getId()).isEqualTo("site1");
        Assertions.assertThat(captor.get().getSite().getName()).isEqualTo("Bigcorp Lyon");
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Optional<Captor> captor = captorDao.findById("unknown");
        Assertions.assertThat(captor).isEmpty();
    }

    @Test
    public void findAll() {
        List<Captor> captors = captorDao.findAll();
        Assertions.assertThat(captors).hasSize(2);
    }

    @Test
    public void create() {
        Assertions.assertThat(captorDao.findAll()).hasSize(2);
        Captor captor = new RealCaptor("New captor", this.site);
        captorDao.save(captor);

        Assertions.assertThat(captorDao.findAll())
                .hasSize(3)
                .extracting(Captor::getName)
                .contains("Eolienne", "Laminoire à chaud", "New captor");
    }

    @Test
    public void update() {
        Optional<Captor> captor = captorDao.findById("c1");
        Assertions.assertThat(captor.get().getName()).isEqualTo("Eolienne");
        captor.get().setName("New name");
        captorDao.save(captor.get());
        captor = captorDao.findById("c1");
        Assertions.assertThat(captor.get().getName()).isEqualTo("New name");
    }

    @Test
    public void deleteById() {
        Captor captor = new RealCaptor("New Captor", site);
        captor.setId("c3");
        em.persist(captor);
        Assertions.assertThat(captorDao.findAll()).hasSize(3);
        captorDao.delete(captorDao.findById("c3").get());
        Assertions.assertThat(captorDao.findAll()).hasSize(2);
    }

    @Test
    public void deleteByIdShouldThrowExceptionWhenIdIsUsedAsForeignKey() {
        Optional<Captor> captor = captorDao.findById("c1");
        Assertions
                .assertThatThrownBy(() -> {
                    captorDao.delete(captor.get());
                    em.flush();
                })
                .isExactlyInstanceOf(PersistenceException.class)
                .hasCauseExactlyInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void findByExample() {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains())
                .withIgnorePaths("id")
                .withIgnoreNullValues();
        Captor captor = new FixedCaptor("Eolienne", site);
        //captor.setName("Eolienne");
        List<Captor> captors = captorDao.findAll(Example.of(captor, matcher));
        Assertions.assertThat(captors)
                .hasSize(1)
                .extracting("id", "name")
                .containsExactly(Tuple.tuple("c1", "Eolienne"));
    }

    @Test
    public void preventConcurrentWrite() {
        Captor captor = captorDao.getOne("c1");
        // A la base le numéro de version est à sa valeur initiale
        Assertions.assertThat(captor.getVersion()).isEqualTo(0);
        // On detache cet objet du contexte de persistence
        em.detach(captor);
        captor.setName("Captor updated");
        // On force la mise à jour en base (via le flush) et on vérifie que l'objet retourné
        // et attaché à la session a été mis à jour
        Captor attachedCaptor = captorDao.save(captor);
        em.flush();
        Assertions.assertThat(attachedCaptor.getName()).isEqualTo("Captor updated");
        Assertions.assertThat(attachedCaptor.getVersion()).isEqualTo(1);
        // Si maintenant je réessaie d'enregistrer captor, comme le numéro de version est
        // à 0 je dois avoir une exception
        Assertions.assertThatThrownBy(() -> captorDao.save(captor))
                .isExactlyInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
}
