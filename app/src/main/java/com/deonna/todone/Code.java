package com.deonna.todone;

public enum Code {

    EDIT_REQUEST(1), EDITED(2);

    private final int value;

    Code(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
