package com.example.computerconfiguration;

public enum Motherboard {
    H610("LGA1700", "DDR4", 2, 3200), B660("LGA1700", "DDR4", 4, 3600),
    B760_DDR5("LGA1700", "DDR5", 4, 6000), B550("AM4", "DDR4", 4, 4600),
    X670("AM5", "DDR5", 4, 6400);

    private final String socket;
    private final String ramGeneration;
    private final int maxRamSlots;
    private final int maxRamFrequency;

    Motherboard(String socket, String ramGeneration, int maxRamSlots, int maxRamFrequency) {
        this.socket = socket;
        this.ramGeneration = ramGeneration;
        this.maxRamSlots = maxRamSlots;
        this.maxRamFrequency = maxRamFrequency;
    }

    public String socket() { return socket; }
    public String ramGeneration() { return ramGeneration; }
    public int maxRamSlots() { return maxRamSlots; }
    public int maxRamFrequency() { return maxRamFrequency; }
}
