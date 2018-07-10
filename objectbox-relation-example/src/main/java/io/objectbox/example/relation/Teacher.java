package io.objectbox.example.relation;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher {

    @Id
    public long id;

    // 'to' is optional if only one relation matches
    @Backlink(to = "teachers")
    public ToMany<Student> students;

}
