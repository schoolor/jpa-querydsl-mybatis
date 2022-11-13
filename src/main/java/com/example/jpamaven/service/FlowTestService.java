package com.example.jpamaven.service;

import com.example.jpamaven.dto.NativeQueryResultDto;
import com.example.jpamaven.dto.OrderDto;
import com.example.jpamaven.entity.Order;
import com.example.jpamaven.entity.QOrder;
import com.example.jpamaven.repository.OrderItemMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlowTestService {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final OrderItemMapper orderItemMapper;

    public FlowTestService(EntityManager entityManager, JPAQueryFactory queryFactory, OrderItemMapper orderItemMapper) {
        this.entityManager = entityManager;
        this.queryFactory = queryFactory;
        this.orderItemMapper = orderItemMapper;
    }


    public void fetchAndUpdate() {
        // select with nativeQuery
        List<Order> nativeOrders = entityManager
                .createNativeQuery(
                        "SELECT orders.* " +
                                "FROM orders INNER JOIN customer ON orders.customer_id = customer.id " +
                                "WHERE customer.name = :customerName",
                        Order.class
                )
                .setParameter("customerName", "customerA")
                .getResultList();

        List<NativeQueryResultDto> customResult = entityManager
                .createNativeQuery(
                        "SELECT customer.name, COUNT(orders.id) order_count " +
                                "FROM orders INNER JOIN customer ON orders.customer_id = customer.id " +
                                "WHERE customer.name = :customerName " +
                                "GROUP BY customer.name ",
                        "orderStatisticsByCustomerNameMapping"
                )
                .setParameter("customerName", "customerA")
                .getResultList();

        // select with querydsl
        QOrder qOrder = new QOrder("order");
        List<Order> orders = queryFactory
                .selectFrom(qOrder)
                .where(
                        qOrder.customer.name.eq("customerA")
                )
                .fetch();

        orders.get(1).getOrderItems().stream()
                .filter(item -> item.getProduct().getName().equals("Piano"))
                .forEach(item -> item.setAmount(item.getAmount() + 5));

        entityManager.flush(); // apply changes

        // delete order1.tv
        orders.get(0).getOrderItems().stream()
                .filter(item -> item.getProduct().getName().equals("TV"))
                .forEach(item -> {
                    orderItemMapper.deleteById(item.getId());

//                    entityManager
//                            .createNativeQuery("DELETE from order_item WHERE id = :itemId")
//                            .setParameter("itemId", item.getId())
//                            .executeUpdate();
                });

        // update order2.piano.amount + 3
        orders.get(1).getOrderItems().stream()
                .filter(item -> item.getProduct().getName().equals("Piano"))
                .forEach(item -> {
                    orderItemMapper.updateAmount(item.getId(), item.getAmount() + 2);

//                    entityManager
//                            .createNativeQuery("UPDATE order_item SET amount = :amount WHERE id = :itemId")
//                            .setParameter("amount", item.getAmount() + 3)
//                            .setParameter("itemId", item.getId())
//                            .executeUpdate();
                });


        // update deleted item
        orders.get(0).getOrderItems().stream()
                .filter(item -> item.getProduct().getName().equals("TV"))
                .forEach(item -> item.setAmount(item.getAmount() + 5));
        // throws org.hibernate.StaleStateException

    }

    public List<OrderDto> checkOrders() {
        QOrder qOrder = new QOrder("order");
        return queryFactory
                .selectFrom(qOrder)
                .where(
                        qOrder.customer.name.eq("customerA")
                )
                .fetch()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList())
                ;

    }
}
