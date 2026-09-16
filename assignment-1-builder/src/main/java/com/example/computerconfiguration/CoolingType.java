package com.example.computerconfiguration;

/**
 * Cooling solution. The enum answers both cooling questions itself, so the
 * Builder does not need a special case for liquid cooling.
 */
public enum CoolingType {

    STOCK_AIR(125, 155, false),
    TOWER_AIR(200, 165, false),
    AIO_LIQUID(280, 60, true);

    private final int maxCpuTdpWatts;
    private final int coolerHeightMm;
    private final boolean radiatorRequired;

    CoolingType(int maxCpuTdpWatts, int coolerHeightMm, boolean radiatorRequired) {
        this.maxCpuTdpWatts = maxCpuTdpWatts;
        this.coolerHeightMm = coolerHeightMm;
        this.radiatorRequired = radiatorRequired;
    }

    public int maxCpuTdpWatts() {
        return maxCpuTdpWatts;
    }

    public boolean canCool(Cpu cpu) {
        return cpu.thermalDesignPowerWatts() <= maxCpuTdpWatts;
    }

    public boolean fitsIn(CaseType caseType) {
        if (radiatorRequired && !caseType.hasRadiatorMount()) {
            return false;
        }
        return coolerHeightMm <= caseType.maxCoolerHeightMm();
    }

    public boolean isEnhanced() {
        return this != STOCK_AIR;
    }
}
