package com.deonna.todone.constants;


public enum Priority {

    LOW(0), MEDIUM(1), HIGH(2);

    private final int value;

    Priority(int value) { this.value = value; }

    public int getValue() { return value; }

    public static Priority fromInt(int value) {

        switch(value) {
            case 0:
                return LOW;
            case 1:
                return MEDIUM;
            case 2:
                return HIGH;
            default:
                return LOW;
        }
    }

    public static String toString(Priority priority) {

        final String LOW_PRIORITY = "Low Priority";
        final String MEDIUM_PRIORITY = "Medium Priority";
        final String HIGH_PRIORITY = "High Priority";

        switch(priority.getValue()) {
            case 0:
                return LOW_PRIORITY;
            case 1:
                return MEDIUM_PRIORITY;
            case 2:
                return HIGH_PRIORITY;
            default:
                return LOW_PRIORITY;
        }
    }
}
