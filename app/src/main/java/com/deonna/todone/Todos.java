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

    private ArrayList<String> mItems;
    private final File mFilesDir;

    public Todos(File fileDir) {
        mFilesDir = fileDir;
        readFromFile();
    }

    public ArrayList<String> getItems() {
        return mItems;
    }

    public boolean add(String text) {

        if(!text.isEmpty()) {
            mItems.add(text);
            writeToFile();
            return true;
        }

        return false;
    }

    public void edit(int position, String text) {
        if(!text.isEmpty()) {
            mItems.set(position, text);
        } else {
            mItems.remove(position);
        }

        writeToFile();
    }

    public void remove(int position) {
        mItems.remove(position);
        writeToFile();
    }

    public String get(int position) {
        return mItems.get(position);
    }

    private void writeToFile() {
        File file =  new File(mFilesDir, FILENAME);

        try {
            FileUtils.writeLines(file, mItems);
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while writing to " + FILENAME + " ", e);
        }
    }

    private void readFromFile() {
        File file =  new File(mFilesDir, FILENAME);

        try {
            mItems = new ArrayList<String>(FileUtils.readLines(file, ENCODING));
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while reading " + FILENAME + " ", e);
        }
    }
}
