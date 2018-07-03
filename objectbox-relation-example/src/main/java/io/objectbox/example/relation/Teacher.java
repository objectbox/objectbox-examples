package io.objectbox.example.relation;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher {

    @Id
    public long id;

    @Backlink
    public ToMany<Student> students;

}
