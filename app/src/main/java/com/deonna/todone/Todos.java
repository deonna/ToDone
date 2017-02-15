package com.deonna.todone;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Todos {

    private static final String TAG = Todos.class.getSimpleName();
    private static final String FILENAME = "todo.txt";
    private static final String ENCODING = "UTF-8";

    private ArrayList<Todo> items;
    private final File filesDir;

    public Todos(File fileDir) {

        filesDir = fileDir;
        readFromFile();
    }

    public ArrayList<Todo> getItems() {

        return items;
    }

    public boolean add(String text) {

        if(!text.isEmpty()) {
            items.add(new Todo(text));
            writeToFile();
            return true;
        }

        return false;
    }

    public void edit(int position, String text) {

        if(!text.isEmpty()) {
            Todo todo = items.get(position);
            todo.setName(text);

            items.set(position, todo);
        } else {
            items.remove(position);
        }

        writeToFile();
    }

    public void remove(int position) {

        items.remove(position);
        writeToFile();
    }

    public Todo get(int position) {

        return items.get(position);
    }

    private void writeToFile() {

        File file =  new File(filesDir, FILENAME);

        ArrayList<String> names = new ArrayList<String>();

        for (Todo todo : items) {
            names.add(todo.getName());
        }

        try {
            FileUtils.writeLines(file, names);
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while writing to " + FILENAME + " ", e);
        }
    }

    private void readFromFile() {

        File file =  new File(filesDir, FILENAME);

        try {
            ArrayList<String> names = new ArrayList<String>(FileUtils.readLines(file, ENCODING));

            items = new ArrayList<Todo>();

            for (String name : names) {
                items.add(new Todo(name));
            }

        } catch (IOException e) {
            Log.e(TAG, "Exception caught while reading " + FILENAME + " ", e);
        }
    }
}
