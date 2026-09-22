package com.example.computerconfiguration;

public enum CaseType {
    MINI_TOWER(2, 160), MID_TOWER(4, 180), FULL_TOWER(6, 200);

    private final int coolingCapacity;
    private final int maxCoolerHeightMm;

    CaseType(int coolingCapacity, int maxCoolerHeightMm) {
        this.coolingCapacity = coolingCapacity;
        this.maxCoolerHeightMm = maxCoolerHeightMm;
    }

    public int coolingCapacity() { return coolingCapacity; }
    public int maxCoolerHeightMm() { return maxCoolerHeightMm; }
}
