package com.example.computerconfiguration;

/**
 * Value object describing the installed memory as a whole.
 *
 * <p>Keeping generation, capacity, frequency and module count together means an
 * incomplete memory specification cannot exist: the compact constructor rejects
 * it at creation time, long before the configuration is built.
 */
public record RamModule(RamType type, int capacityGb, int frequencyMhz, int moduleCount) {

    private static final int MIN_CAPACITY_GB = 4;
    private static final int MIN_FREQUENCY_MHZ = 800;
    private static final int MAX_MODULE_COUNT = 4;
    private static final int DEFAULT_MODULE_COUNT = 2;

    public RamModule {
        if (type == null) {
            throw new IllegalArgumentException("RAM type is required");
        }
        if (capacityGb < MIN_CAPACITY_GB) {
            throw new IllegalArgumentException(
                    "RAM capacity must be at least " + MIN_CAPACITY_GB + " GB, but was " + capacityGb);
        }
        if (frequencyMhz < MIN_FREQUENCY_MHZ) {
            throw new IllegalArgumentException(
                    "RAM frequency must be at least " + MIN_FREQUENCY_MHZ + " MHz, but was " + frequencyMhz);
        }
        if (frequencyMhz > type.maxFrequencyMhz()) {
            throw new IllegalArgumentException(
                    type + " cannot run at " + frequencyMhz + " MHz (generation limit is "
                            + type.maxFrequencyMhz() + " MHz)");
        }
        if (moduleCount < 1 || moduleCount > MAX_MODULE_COUNT) {
            throw new IllegalArgumentException(
                    "Module count must be between 1 and " + MAX_MODULE_COUNT + ", but was " + moduleCount);
        }
    }

    public static RamModule of(RamType type, int capacityGb, int frequencyMhz) {
        return new RamModule(type, capacityGb, frequencyMhz, DEFAULT_MODULE_COUNT);
    }

    public boolean fitsOn(Motherboard motherboard) {
        return type == motherboard.supportedRamType()
                && moduleCount <= motherboard.memorySlots()
                && frequencyMhz <= motherboard.maxMemoryFrequencyMhz();
    }

    @Override
    public String toString() {
        return capacityGb + "GB " + type + "-" + frequencyMhz + " (" + moduleCount + " modules)";
    }
}
