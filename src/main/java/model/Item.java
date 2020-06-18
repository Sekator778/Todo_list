package model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * модель данных
 */
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * identity task
     */
    @Column(name = "id")
    private int id;
    /**
     * description current task
     */
    @Column(name = "description")
    private String description;

    /**
     * date created task
     */
    @Column(name = "created")
    private LocalDate date;

    /**
     *  done - if выполнена, undone - else нет
     */
    @Column(name = "done")
    private String done;

    /**
     * constructor for JPA
     */
    public Item() {
    }

    public Item(String description, LocalDate date, String done) {
        this.description = description;
        this.date = date;
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (date != null ? !date.equals(item.date) : item.date != null) return false;
        return done != null ? done.equals(item.done) : item.done == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (done != null ? done.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", done='" + done + '\'' +
                '}';
    }

    /**
     * !!!!!!!!!!
     */
    public String toJsonString() {
        return "{" +
                "\"id\"" + ":" + id + "," +
                "\"description\"" + ":" + "\"" + description + "\"," +
                "\"date\"" + ":" + "\"" + date + "\"," +
                "\"done\"" + ":" + "\"" + done + "\"" +
                "}";
    }
}

