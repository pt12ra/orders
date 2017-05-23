package lt.vu.mif.lino2234.controllers;

import lombok.Getter;
import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.bo.impl.AsyncCalculatorBo;
import lt.vu.mif.lino2234.views.OrderView;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Named
@ViewScoped
public class OrdersController implements Serializable {

    @Inject private OrderBo orderBo;
    @Inject private AsyncCalculatorBo asyncCalculatorBo;

    @Getter private OrderView selectedOrder = new OrderView();
    @Getter private OrderView conflictingOrder;
    @Getter private List<OrderView> orders;
    @Getter private String count;

    private Future<Long> resultInFuture = null;

    public void callAsyncMethod() throws ExecutionException, InterruptedException {
        if (resultInFuture == null) {
            resultInFuture = asyncCalculatorBo.asyncMethod();
            count =  "I just have started counting entities";
        } else {
            String result = resultInFuture.get().toString();
            resultInFuture = null;
            count =  "Result is finally ready, and it is: " + result;
        }
    }

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
        try{
            callAsyncMethod();
        } catch (ExecutionException e) {
            count = "ExecutionException";
        } catch (InterruptedException e) {
            count = "InterruptedException";
        }

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

    public void updateSelectedOrder() {
        try {
            orderBo.saveToEntity(selectedOrder);
            reloadAll();
        } catch (OptimisticLockException ole) {
            conflictingOrder = orderBo.findOne(selectedOrder.getId());
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
        }
    }

    public void overwriteOrder() {
        selectedOrder.setOptLockVersion(conflictingOrder.getOptLockVersion());
        updateSelectedOrder();
    }
}
