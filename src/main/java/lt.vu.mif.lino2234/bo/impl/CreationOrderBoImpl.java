package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.entities.Order;
import lt.vu.mif.lino2234.views.OrderView;

import javax.enterprise.inject.Specializes;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Specializes
public class CreationOrderBoImpl extends OrderBoImpl {

    @Transactional
    public Order createEntity(String author, String title) {
        Objects.requireNonNull(author, "Object 'view' must not be null");
        Objects.requireNonNull(title, "Object 'view' must not be null");

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
}
