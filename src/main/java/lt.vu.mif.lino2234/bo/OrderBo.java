package lt.vu.mif.lino2234.bo;

import lt.vu.mif.lino2234.entities.Order;

import java.util.List;

public interface OrderBo {

    Order saveToEntity(Order view);
    Order findOne(Long id);
    void delete(Long id);
    List<Order> getAll();
    Order createEntity(String author, String title);
}
