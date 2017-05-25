package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.RescueOrAsync;
import lt.vu.mif.lino2234.entities.Order;
import org.apache.deltaspike.core.api.future.Futureable;

import javax.ejb.AsyncResult;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.concurrent.Future;

@Named
@ApplicationScoped
public class AsyncCalculatorBo implements Serializable{

    @Inject
    @RescueOrAsync
    private EntityManager em;

    private Long count;

    @Futureable
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Future<Long> asyncMethod() {
        try {
            Thread.sleep(3000);
            CriteriaBuilder qb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = qb.createQuery(Long.class);
            cq.select(qb.count(cq.from(Order.class)));
            count =  em.createQuery(cq).getSingleResult();
        } catch (InterruptedException e) {
        }

        return new AsyncResult<>(count);
    }
}
