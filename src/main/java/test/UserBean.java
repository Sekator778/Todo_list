package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

/**
 *
 */

public class UserBean {
    private int id;
    private String name;

    public UserBean() {
    }

    public UserBean(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("surname")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return "First class";
    }
}
