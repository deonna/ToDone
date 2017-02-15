package com.deonna.todone;

public class Todo {

    private long id;
    private String name;

    public Todo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long newId) {
        id = newId;
    }

    public long getId() {
        return id;
    }
}
