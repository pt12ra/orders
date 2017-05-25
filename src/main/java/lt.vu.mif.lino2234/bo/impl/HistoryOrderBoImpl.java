package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.dao.OrderDao;
import lt.vu.mif.lino2234.entities.Order;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Alternative
@ConversationScoped
public class HistoryOrderBoImpl implements OrderBo, Serializable {

    private static final long serialVersionUID = 6174555929309478575L;
    private static String CHANGED = "--changed--";

    @Inject
    protected OrderDao orderDao;

    @Override
    @Transactional
    public Order saveToEntity(Order entity) {
        Objects.requireNonNull(entity, "Object 'entity' must not be null");

        try {
            if(entity.getId() != null) {
                Order newEntity = new Order();
                newEntity.setAuthor(entity.getAuthor());
                newEntity.setTitle(entity.getTitle());
                newEntity.setComment(entity.getComment());
                newEntity.setQuantity(entity.getQuantity());
                newEntity.setPrice(entity.getPrice());
                newEntity.setLastChanged(LocalDateTime.now());

                Order oldEntity = findOne(entity.getId());
                oldEntity.setTitle(CHANGED);
                oldEntity.setComment(CHANGED);
                oldEntity.setQuantity(null);
                oldEntity.setPrice(null);
                orderDao.save(oldEntity);
                entity.setLastChanged(LocalDateTime.now());
                return orderDao.save(newEntity);
            } else {
                entity.setLastChanged(LocalDateTime.now());
                return orderDao.save(entity);
            }
        } catch (OptimisticLockException e) {
            throw e;
        }
    }

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

        throw new NotImplementedException();
    }
}
