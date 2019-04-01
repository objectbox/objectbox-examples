package io.objectbox.example;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Student {

    @Id
    private long id;

    private ToMany<Teacher> teachers;

    public Student(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
