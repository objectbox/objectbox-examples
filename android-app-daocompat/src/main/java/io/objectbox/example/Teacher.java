package io.objectbox.example;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Teacher {

    @Id
    private long id;

    public Teacher(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
