package io.objectbox.example;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Order {

    @Id
    private long id;

    private ToOne<Customer> customer;

    public Order(Long id, long customerId) {
        this.id = id;
        this.customer.setTargetId(customerId);
    }

    public long getId() {
        return id;
    }

    public Order setId(long id) {
        this.id = id;
        return this;
    }

    public ToOne<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer.setTarget(customer);
    }

    public void setCustomerId(long customerId) {
        this.customer.setTargetId(customerId);
    }

    public long getCustomerId() {
        return customer.getTargetId();
    }
}
