package lt.vu.mif.lino2234.controllers;

import lombok.Getter;
import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.views.OrderView;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class OrdersController implements Serializable {

    @Inject private OrderBo orderBo;

    @Getter private OrderView selectedOrder = new OrderView();
    @Getter private OrderView conflictingOrder = new OrderView();
    @Getter private List<OrderView> orders;

    @PostConstruct
    public void init() {
        reloadAll();
    }

    public void prepareForEditing(OrderView order) {
        selectedOrder = order;
        conflictingOrder = null;
    }

    public void reloadAll() {
        orders = orderBo.getAll();
        selectedOrder = new OrderView();
    }

    public void createFoodOrder() {
        orderBo.saveToEntity(selectedOrder);
        reloadAll();
    }

    public void deleteOrder(OrderView order) {
        if (order != null && order.getId() != null) {
            orderBo.delete(order.getId());
        }
        reloadAll();
    }
}
