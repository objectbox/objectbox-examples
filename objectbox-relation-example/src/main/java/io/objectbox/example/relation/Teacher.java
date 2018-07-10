package io.objectbox.example.relation;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher {

    @Id
    public long id;

    public String name;

    // 'to' is optional if only one relation matches
    @Backlink(to = "teachers")
    public ToMany<Student> students;

    // used by ObjectBox to init relations
    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
    }
}
