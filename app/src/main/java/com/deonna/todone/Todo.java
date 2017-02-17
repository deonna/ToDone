package com.deonna.todone;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo implements Serializable {

    private static final String TAG = Todo.class.getSimpleName() ;

    private long id;
    private String name;
    private Priority priority;
    private boolean isCompleted;
    private Date dueDate;
    private String dueDateText;

    private static final long serialVersionUID = 1L;

    private final TodoDataSource todosDataSource;

    public Todo(String name) {

        this.name = name;
        priority = Priority.LOW;
        isCompleted = false;
        dueDate = null;
        dueDateText = "";

        todosDataSource = Todos.getDataSource();
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

    public Priority getPriority() {

        return priority;
    }

    public void setPriority(Priority priority) {

        this.priority = priority;
    }

    public void setPriorityFromInt(int priority) {

        this.priority = Priority.fromInt(priority);
    }

    public boolean getIsCompleted() {

        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {

        this.isCompleted = isCompleted;
    }

    public int getIsCompletedAsInt() {

        if (isCompleted) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setIsCompleted(int completed) {

        if (completed == 0) {
            isCompleted = false;
        } else {
            isCompleted = true;
        }
    }

    public Date getDueDate() {

        return dueDate;
    }

    public void setDueDate(Date dueDate) {

        this.dueDate = dueDate;
        setDueDateText(getDueDateText());
    }

    public void addToDataSource() {

        todosDataSource.create(this);
    }

    public void updateInDataSource() {

        todosDataSource.update(this);
    }

    public void removeFromDataSource() {

        todosDataSource.delete(getId());
    }

    public String getDueDateText() {

        if (dueDate == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
            dueDateText = formatter.format(dueDate);

            return dueDateText;
        }
    }

    public void setDueDateText(String dueDateText) {
        
        if (!dueDateText.isEmpty()) {
            this.dueDateText = dueDateText;

            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");

            try {
                dueDate = formatter.parse(dueDateText);
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing due date: " + e);
            }
        }
    }
}
