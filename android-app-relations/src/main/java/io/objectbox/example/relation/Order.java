package io.objectbox.example.relation;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Order {

    @Id
    public long id;

    public ToOne<Customer> customer;

}
