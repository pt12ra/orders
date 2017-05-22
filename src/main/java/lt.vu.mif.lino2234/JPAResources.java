package lt.vu.mif.lino2234;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.SynchronizationType;

@ApplicationScoped
public class JPAResources {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    @RequestScoped
    private EntityManager createJTAEntityManager() {
        return emf.createEntityManager(SynchronizationType.SYNCHRONIZED);
    }

    private void closeUnsynchronizedEntityManager(@Disposes EntityManager em) {
        em.close();
    }


}
