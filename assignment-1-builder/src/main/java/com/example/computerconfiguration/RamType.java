package com.example.computerconfiguration;

/**
 * Memory generation together with the highest frequency the generation itself
 * can reach. A concrete motherboard may support less than this.
 */
public enum RamType {

    DDR3(2133),
    DDR4(4800),
    DDR5(8000);

    private final int maxFrequencyMhz;

    RamType(int maxFrequencyMhz) {
        this.maxFrequencyMhz = maxFrequencyMhz;
    }

    public int maxFrequencyMhz() {
        return maxFrequencyMhz;
    }
}
