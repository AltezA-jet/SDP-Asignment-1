package com.example.computerconfiguration;

public enum Cpu {
    CELERON(1, "LGA1700", 45), I3(2, "LGA1700", 65), I5(3, "LGA1700", 125),
    I7(4, "LGA1700", 190), I9(5, "LGA1700", 253), RYZEN5(3, "AM4", 105),
    RYZEN7(4, "AM5", 170);

    private final int performanceLevel;
    private final String socket;
    private final int tdp;

    Cpu(int level, String socket, int tdp) {
        performanceLevel = level; this.socket = socket; this.tdp = tdp;
    }

    public int performanceLevel() { return performanceLevel; }
    public String socket() { return socket; }
    public int tdp() { return tdp; }
}
