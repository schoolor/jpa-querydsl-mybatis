package com.example.jpamaven.initialize;

import com.example.jpamaven.entity.Customer;
import com.example.jpamaven.entity.Order;
import com.example.jpamaven.entity.OrderItem;
import com.example.jpamaven.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;

@Component
@Profile({"local", "test"})
@Slf4j
public class DataLoader implements ApplicationRunner {
    private final EntityManager entityManager;

    public DataLoader(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        log.info("Load initial data");

        Product toaster = createProduct("Toaster", 100);
        Product tv = createProduct("TV", 3000);
        Product piano = createProduct("Piano", 4000);

        Customer customerA = createCustomer("customerA", "customer.a@some.domain");
        Customer customerB = createCustomer("customerB", "customer.b@some.domain");
        Customer customerC = createCustomer("customerC", "customer.c@some.domain");

        Order order1 = OrderBuilder.builder()
                .customer(customerA)
                .orderItem(toaster, 2)
                .orderItem(tv, 1)
                .build();
        Order order2 = OrderBuilder.builder()
                .customer(customerA)
                .orderItem(piano, 1)
                .build();
        Order order3 = OrderBuilder.builder()
                .customer(customerB)
                .orderItem(tv, 2)
                .build();
        Order order4 = OrderBuilder.builder()
                .customer(customerB)
                .orderItem(piano, 5)
                .build();
        Order order5 = OrderBuilder.builder()
                .customer(customerB)
                .orderItem(piano, 3)
                .build();

        persist(toaster, tv, piano);
        persist(customerA, customerB, customerC);
        persist(order1, order2, order3, order4, order5);
    }

    private void persist(Object... objects) {
        for (Object object : objects) {
            entityManager.persist(object);
        }
    }
    private void persist(Order... orders) {
        for (Order order : orders) {
            entityManager.persist(order);
            for (OrderItem item : order.getOrderItems()) {
                entityManager.persist(item);
            }
        }
    }

    private static class OrderBuilder {
        private Order order;
        private OrderBuilder() {
            this.order = new Order();
        }
        public Order build() {
            return this.order;
        }
        public OrderBuilder customer(Customer customer) {
            order.setCustomer(customer);
            return this;
        }
        public OrderBuilder orderItem(Product product, int amount) {
            this.order.addItem(new OrderItem(product, amount));
            return this;
        }
        public static OrderBuilder builder() {
            return new OrderBuilder();
        }
    }

    private Product createProduct(String name, int listPrice) {
        Product product = new Product();
        product.setName(name);
        product.setListPrice(listPrice);
        return product;
    }
    private Customer createCustomer(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        return customer;
    }
}
