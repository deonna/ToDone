package com.deonna.todone.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.deonna.todone.constants.Priority;
import com.deonna.todone.database.TodoDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo implements Parcelable {

    private static final String TAG = Todo.class.getSimpleName() ;
    private static final String FORMAT_PATTERN = "MMM d, yyyy";

    private long id;
    private String name;
    private Priority priority;
    private boolean isCompleted;
    private Date dueDate;
    private String dueDateText;
    private String note;

    private static TodoDataSource todosDataSource;

    public Todo(String name) {

        this.name = name;
        priority = Priority.LOW;
        isCompleted = false;
        dueDate = null;
        dueDateText = "";
        note = "";

//        todosDataSource = Todos.getDataSource();
    }

    private Todo(Parcel in) {
        id = in.readLong();
        name = in.readString();
        priority = Priority.fromInt(in.readInt());
        isCompleted = in.readInt() != 0;
        dueDateText = in.readString();
        note = in.readString();
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

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

        isCompleted = completed != 0;
    }

    public String getDueDateText() {

        if (dueDate == null) {
            return "";
        } else {
            dueDateText = getFormattedDate(dueDate);
            return dueDateText;
        }
    }

    public static String getFormattedDate(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN);

        return formatter.format(date);
    }

    public void setDueDateText(String dueDateText) {

        if (!dueDateText.isEmpty()) {
            this.dueDateText = dueDateText;
            dueDate = formatDueDate(dueDateText);
        }
    }

    public static Date formatDueDate(String dueDateText) {

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN);

        try {
             return formatter.parse(dueDateText);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing due date: " + e);
        }

        return null;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeLong(id);
        out.writeString(name);
        out.writeInt(priority.getValue());

        int completedInt = isCompleted ? 1 : 0;
        out.writeInt(completedInt);

        out.writeString(dueDateText);
        out.writeString(note);
    }
}
