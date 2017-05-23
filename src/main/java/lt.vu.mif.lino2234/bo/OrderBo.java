package lt.vu.mif.lino2234.bo;

import lt.vu.mif.lino2234.views.OrderView;

import java.util.List;

public interface OrderBo {

    OrderView saveToEntity(OrderView view);
    OrderView findOne(Long id);
    void delete(Long id);
    List<OrderView> getAll();
    OrderView createEntity(String author, String title);
}
