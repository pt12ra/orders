package lt.vu.mif.lino2234.dao.impl;

import lt.vu.mif.lino2234.dao.OrderDao;
import lt.vu.mif.lino2234.entities.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
@Named(value = "orderDao")
public class OrderDaoImpl implements OrderDao {

    @Inject
    private EntityManager em;

    @Override
    public Order save(Order entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    @Override
    public Order update(Order entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Order entity = em.find(Order.class, id);
        em.remove(entity);
    }

    @Override
    public List<Order> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        CriteriaQuery<Order> all = cq.select(root);
        TypedQuery<Order> query = em.createQuery(all);
        return query.getResultList();
    }
}
