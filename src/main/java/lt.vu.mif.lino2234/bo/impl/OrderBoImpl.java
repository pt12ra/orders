package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.dao.OrderDao;
import lt.vu.mif.lino2234.entities.Order;
import lt.vu.mif.lino2234.views.OrderView;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Named(value = "orderBo")
@ConversationScoped
public class OrderBoImpl implements OrderBo, Serializable {

    private static final long serialVersionUID = 2250290709240478151L;
    @Inject
    protected OrderDao orderDao;

    @Inject AsyncCalculatorBo asyncCalculatorBo;

    @Override
    @Transactional
    public Order saveToEntity(Order entity) {
        Objects.requireNonNull(entity, "Object 'entity' must not be null");

        try {
            entity.setLastChanged(LocalDateTime.now());
            return entity.getId() == null ? orderDao.save(entity) : orderDao.update(entity);
        } catch (OptimisticLockException e) {
            throw e;
        }
    }

//    @Override
//    @Transactional
//    public OrderView saveToEntity(OrderView view) {
//        Objects.requireNonNull(view, "Object 'view' must not be null");
//
//        try {
//            Order entity = view.getId() != null ? orderDao.findOne(view.getId()) : new Order();
//            entity.setId(view.getId());
//            entity.setAuthor(view.getAuthor());
//            entity.setTitle(view.getTitle());
//            entity.setComment(view.getComment());
//            entity.setQuantity(view.getQuantity());
//            entity.setPrice(view.getPrice());
//            entity.setLastChanged(LocalDateTime.now());
//            entity.setOptLockVersion(view.getOptLockVersion());
//            return buildOrderView(entity.getId() == null ? orderDao.save(entity) : orderDao.update(entity));
//        } catch (Exception e) {
//            throw new OptimisticLockException();
//        }
//    }

    @Override
    @Transactional
    public Order findOne(Long id) {
        Objects.requireNonNull(id, "Object 'id' must not be null");

        return orderDao.findOne(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Object 'id' must not be null");

        orderDao.delete(id);
    }

    @Override
    @Transactional
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Transactional
    public Order createEntity(String author, String title) {
        Objects.requireNonNull(author, "Object 'author' must not be null");
        Objects.requireNonNull(title, "Object 'title' must not be null");

        try {
            Order entity = new Order();
            entity.setAuthor(author);
            entity.setTitle(title);
            entity.setLastChanged(LocalDateTime.now());
            return this.orderDao.save(entity);
        } catch (Exception e) {
            throw new OptimisticLockException();
        }
    }

    protected OrderView buildOrderView (Order entity) {
        Objects.requireNonNull(entity, "Object 'entity' must not be null");

        OrderView view = new OrderView();
        view.setId(entity.getId());
        view.setAuthor(entity.getAuthor());
        view.setTitle(entity.getTitle());
        view.setComment(entity.getComment());
        view.setQuantity(entity.getQuantity());
        view.setPrice(entity.getPrice());
        view.setLastChanged(entity.getLastChanged().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        view.setOptLockVersion(entity.getOptLockVersion());
        return view;
    }


}
