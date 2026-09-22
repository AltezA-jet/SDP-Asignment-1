package com.example.computerconfiguration;

public enum RamType {
    DDR3("DDR3", 1600), DDR4("DDR4", 3600), DDR5("DDR5", 6000);

    private final String generation;
    private final int frequency;

    RamType(String generation, int frequency) {
        this.generation = generation;
        this.frequency = frequency;
    }

    public String generation() { return generation; }
    public int frequency() { return frequency; }
}
