package io.objectbox.example;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Student {

    @Id
    public long id;

    public ToMany<Teacher> teachers;

}
