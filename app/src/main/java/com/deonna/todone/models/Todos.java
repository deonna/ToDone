package com.deonna.todone.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.deonna.todone.database.TodoDataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import rx.Observable;
import rx.subjects.ReplaySubject;

public class Todos implements Parcelable {

    ReplaySubject<Todos> notifier = ReplaySubject.create();

    private final ArrayList<Todo> items;
    private static TodoDataSource todosDataSource;

    public Todos() {

        todosDataSource = TodoDataSource.getInstance();
        items = todosDataSource.getAll();
    }

    public ArrayList<Todo> getItems() {

        return items;
    }

    public static TodoDataSource getDataSource() {
        return todosDataSource;
    }

    public boolean add(String text) {

        if(!text.isEmpty()) {
            Todo todo = new Todo(text);

            items.add(todo);
            Todo.addToDataSource(todo);
            notifier.onNext(this);
            return true;
        }

        return false;
    }

    public void remove(int position) {

        Todo todo = items.get(position);
        Todo.removeFromDataSource(todo.getId());
        items.remove(position);
        notifier.onNext(this);
    }

    public Todo get(int index) {

        return items.get(index);
    }

    public int size() {

        return items.size();
    }

    public Observable<Todos> asObservable() {
        return notifier;
    }

    public void sort() {

        Collections.sort(items, new Comparator<Todo>() {
            @Override
            public int compare(Todo todo1, Todo todo2) {

                int priority1 = todo1.getPriority().getValue();
                int priority2 = todo2.getPriority().getValue();

                if (priority1 < priority2) {
                    return 1;
                } else if (priority1 > priority2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    /* PARCELABLE */

    protected Todos(Parcel in) {

        todosDataSource = TodoDataSource.getInstance();
        items = in.createTypedArrayList(Todo.CREATOR);
    }

    public static final Creator<Todos> CREATOR = new Creator<Todos>() {
        @Override
        public Todos createFromParcel(Parcel in) {
            return new Todos(in);
        }

        @Override
        public Todos[] newArray(int size) {
            return new Todos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeTypedList(items);
    }
}
