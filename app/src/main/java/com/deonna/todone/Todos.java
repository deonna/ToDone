package com.deonna.todone;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Todos {
    private static final String TAG = Todos.class.getSimpleName();
    private static final String FILENAME = "todo.txt";
    private static final String ENCODING = "UTF-8";

    private ArrayList<String> items;
    private final File filesDir;

    public Todos(File fileDir) {
        filesDir = fileDir;
        readFromFile();
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public boolean add(String text) {

        if(!text.isEmpty()) {
            items.add(text);
            writeToFile();
            return true;
        }

        return false;
    }

    public void edit(int position, String text) {
        if(!text.isEmpty()) {
            items.set(position, text);
        } else {
            items.remove(position);
        }

        writeToFile();
    }

    public void remove(int position) {
        items.remove(position);
        writeToFile();
    }

    public String get(int position) {
        return items.get(position);
    }

    private void writeToFile() {
        File file =  new File(filesDir, FILENAME);

        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while writing to " + FILENAME + " ", e);
        }
    }

    private void readFromFile() {
        File file =  new File(filesDir, FILENAME);

        try {
            items = new ArrayList<String>(FileUtils.readLines(file, ENCODING));
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while reading " + FILENAME + " ", e);
        }
    }
}
