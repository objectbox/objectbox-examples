package io.objectbox.example;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Note {

    @Id
    long id;

    String text;
    String comment;
    Date date;
    ToOne<Note> parent;

    @Backlink
    ToMany<Note> children;

    public Note(Long id) {
        this.id = id;
    }

    public Note(long id, String text, String comment, Date date) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }

    public Note() {
    }

    public Note(String text) {
        this.text = text;
        date = new Date();
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Note getParent() {
        return parent.getTarget();
    }

    public void setParent(Note parent) {
        this.parent.setTarget(parent);
    }

    public ToMany<Note> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Note \"" + text + "\" on " + date;
    }
}
