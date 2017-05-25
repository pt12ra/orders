package lt.vu.mif.lino2234.controllers;

import lombok.Getter;
import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.bo.impl.AsyncCalculatorBo;
import lt.vu.mif.lino2234.entities.Order;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.TransactionalException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Named
@ViewScoped
public class OrdersController implements Serializable {

    @Inject private OrderBo orderBo;
    @Inject private AsyncCalculatorBo asyncCalculatorBo;

    @Getter private Order selectedOrder = new Order();
    @Getter private Order conflictingOrder;
    @Getter private List<Order> orders;
    @Getter private String count;

    private Future<Long> resultInFuture = null;

    public void callAsyncMethod() throws ExecutionException, InterruptedException {
        if (resultInFuture == null) {
            resultInFuture = asyncCalculatorBo.asyncMethod();
            count =  "Please wait. Counting orders";
        } else {
            String result = resultInFuture.get().toString();
            resultInFuture = null;
            count =  "Processing done: Counted " + result.toString() + " orders ";
        }
    }

    @PostConstruct
    public void init() {
        reloadAll();
    }

    public void prepareForEditing(Order order) {
        selectedOrder = order;
        conflictingOrder = null;
    }

    public void reloadAll() {
        orders = orderBo.getAll();
        selectedOrder = new Order();
        try{
            resultInFuture = null;
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

    public void deleteOrder(Order order) {
        if (order != null && order.getId() != null) {
            orderBo.delete(order.getId());
        }
        reloadAll();
    }

    public void updateSelectedOrder() {
        try {
            orderBo.saveToEntity(selectedOrder);
            reloadAll();
        } catch (TransactionalException te) {
            if (!(te.getCause() instanceof OptimisticLockException)){
                return;
            }
            conflictingOrder = orderBo.findOne(selectedOrder.getId());
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
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
