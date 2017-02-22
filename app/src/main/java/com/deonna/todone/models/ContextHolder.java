package com.deonna.todone.models;

import android.content.Context;

public class ContextHolder {

    public static Context context;

    public static void setContext(Context context) {

        ContextHolder.context = context;
    }

    public static Context getContext() {

        return context;
    }
}
