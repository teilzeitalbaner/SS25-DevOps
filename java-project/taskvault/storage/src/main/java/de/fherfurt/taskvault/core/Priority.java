package de.fherfurt.taskvault.core;

public enum Priority {

    LOW_IMPORTANCE(0),
    AVERAGE_IMPORTANCE(1),
    HIGH_IMPORTANCE(2);

    private final int value;

    Priority(int prioValue) {
        this.value = prioValue;
    }

    public int getValue() {
        return value;
    }
}
