package com.example.computerconfiguration;

/**
 * A named class of machine. Each constant carries the minimum requirements that
 * a configuration of that class has to satisfy, so the Builder can check a
 * preset by reading data instead of by comparing strings.
 */
public enum Preset {

    CUSTOM(4, false, false, 1),
    BASIC(4, false, false, 1),
    STUDY(12, false, false, 1),
    GAMING(16, true, true, 3);

    private final int minRamGb;
    private final boolean discreteGpuRequired;
    private final boolean enhancedCoolingRequired;
    private final int minComponentPerformanceLevel;

    Preset(int minRamGb,
           boolean discreteGpuRequired,
           boolean enhancedCoolingRequired,
           int minComponentPerformanceLevel) {
        this.minRamGb = minRamGb;
        this.discreteGpuRequired = discreteGpuRequired;
        this.enhancedCoolingRequired = enhancedCoolingRequired;
        this.minComponentPerformanceLevel = minComponentPerformanceLevel;
    }

    public int minRamGb() {
        return minRamGb;
    }

    public boolean requiresDiscreteGpu() {
        return discreteGpuRequired;
    }

    public boolean requiresEnhancedCooling() {
        return enhancedCoolingRequired;
    }

    public int minComponentPerformanceLevel() {
        return minComponentPerformanceLevel;
    }
}
