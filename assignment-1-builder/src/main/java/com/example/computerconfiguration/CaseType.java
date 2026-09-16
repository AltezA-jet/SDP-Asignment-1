package com.example.computerconfiguration;

/**
 * Chassis size. It limits the height of a tower cooler and decides whether a
 * liquid-cooling radiator can be mounted at all.
 */
public enum CaseType {

    MINI_TOWER(160, false),
    MID_TOWER(180, true),
    FULL_TOWER(200, true);

    private final int maxCoolerHeightMm;
    private final boolean radiatorMountAvailable;

    CaseType(int maxCoolerHeightMm, boolean radiatorMountAvailable) {
        this.maxCoolerHeightMm = maxCoolerHeightMm;
        this.radiatorMountAvailable = radiatorMountAvailable;
    }

    public int maxCoolerHeightMm() {
        return maxCoolerHeightMm;
    }

    public boolean hasRadiatorMount() {
        return radiatorMountAvailable;
    }
}
