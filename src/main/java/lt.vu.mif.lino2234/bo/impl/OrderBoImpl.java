package lt.vu.mif.lino2234.bo.impl;

import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.dao.OrderDao;
import lt.vu.mif.lino2234.entities.Order;
import lt.vu.mif.lino2234.views.OrderView;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named(value = "orderBo")
@RequestScoped
public class OrderBoImpl implements OrderBo {
    
    @Inject
    private OrderDao orderDao;

    @Inject AsyncCalculatorBo asyncCalculatorBo;

    @Override
    @Transactional
    public OrderView saveToEntity(OrderView view) {
        Objects.requireNonNull(view, "Object 'view' must not be null");

        Order entity = view.getId() != null ? orderDao.findOne(view.getId()) : new Order();
        entity.setId(view.getId());
        entity.setAuthor(view.getAuthor());
        entity.setTitle(view.getTitle());
        entity.setComment(view.getComment());
        entity.setQuantity(view.getQuantity());
        entity.setPrice(view.getPrice());
        entity.setLastChanged(LocalDateTime.now());
        entity.setOptLockVersion(view.getOptLockVersion());
        return buildOrderView(entity.getId() == null ? orderDao.save(entity) : orderDao.update(entity));
    }

    @Override
    @Transactional
    public OrderView findOne(Long id) {
        Objects.requireNonNull(id, "Object 'id' must not be null");

        return buildOrderView(orderDao.findOne(id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Object 'id' must not be null");

        orderDao.delete(id);
    }

    @Override
    @Transactional
    public List<OrderView> getAll() {
        return orderDao.getAll().stream().map(this::buildOrderView).collect(Collectors.toList());
    }
    
    private OrderView buildOrderView (Order entity) {
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
