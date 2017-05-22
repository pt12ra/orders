package lt.vu.mif.lino2234.dao;

import lt.vu.mif.lino2234.entities.Order;

import java.util.List;

public interface OrderDao {

    Order save(Order entity);
    Order findOne(Long id);
    Order update(Order entity);
    void delete(Long id);
    List<Order> getAll();
}
