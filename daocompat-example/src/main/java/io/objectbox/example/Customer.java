package io.objectbox.example;

import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Customer {

    @Id
    private long id;

    @Backlink
    private ToMany<Order> orders;

    public Customer(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Customer setId(long id) {
        this.id = id;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

}
