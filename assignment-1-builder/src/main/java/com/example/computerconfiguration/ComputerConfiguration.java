package com.example.computerconfiguration;

/**
 * Immutable computer configuration. Instances can only be produced by
 * {@link Builder}, which is the single place where a configuration is checked.
 */
public final class ComputerConfiguration {

    private final String name;
    private final Preset preset;
    private final Cpu cpu;
    private final Gpu gpu;
    private final Motherboard motherboard;
    private final RamModule ram;
    private final int storageGb;
    private final int powerSupplyWatts;
    private final boolean solidStateStorage;
    private final boolean wifi;
    private final boolean bluetooth;
    private final CaseType caseType;
    private final CoolingType coolingType;

    private ComputerConfiguration(Builder builder) {
        this.name = builder.name;
        this.preset = builder.preset;
        this.cpu = builder.cpu;
        this.gpu = builder.gpu;
        this.motherboard = builder.motherboard;
        this.ram = builder.ram;
        this.storageGb = builder.storageGb;
        this.powerSupplyWatts = builder.powerSupplyWatts;
        this.solidStateStorage = builder.solidStateStorage;
        this.wifi = builder.wifi;
        this.bluetooth = builder.bluetooth;
        this.caseType = builder.caseType;
        this.coolingType = builder.coolingType;
    }

    public static Builder builder(String name, Cpu cpu, Motherboard motherboard, RamModule ram) {
        return new Builder(name, cpu, motherboard, ram);
    }

    public String name() {
        return name;
    }

    public Preset preset() {
        return preset;
    }

    public Cpu cpu() {
        return cpu;
    }

    public Gpu gpu() {
        return gpu;
    }

    public Motherboard motherboard() {
        return motherboard;
    }

    public RamModule ram() {
        return ram;
    }

    public int storageGb() {
        return storageGb;
    }

    public int powerSupplyWatts() {
        return powerSupplyWatts;
    }

    public boolean hasSolidStateStorage() {
        return solidStateStorage;
    }

    public boolean hasWifi() {
        return wifi;
    }

    public boolean hasBluetooth() {
        return bluetooth;
    }

    public CaseType caseType() {
        return caseType;
    }

    public CoolingType coolingType() {
        return coolingType;
    }

    @Override
    public String toString() {
        return "ComputerConfiguration{" + name
                + ", preset=" + preset
                + ", cpu=" + cpu
                + ", gpu=" + gpu
                + ", board=" + motherboard
                + ", ram=" + ram
                + ", storage=" + storageGb + "GB"
                + ", psu=" + powerSupplyWatts + "W"
                + ", case=" + caseType
                + ", cooling=" + coolingType + "}";
    }

    /** Fluent builder for {@link ComputerConfiguration}. */
    public static final class Builder {

        private static final int DEFAULT_STORAGE_GB = 256;
        private static final int DEFAULT_PSU_WATTS = 450;
        private static final int MIN_STORAGE_GB = 128;
        private static final int MIN_PSU_WATTS = 250;
        private static final int MAX_BOTTLENECK_CPU_LEVEL = 2;
        private static final int MIN_BOTTLENECK_GPU_LEVEL = 4;

        private final String name;
        private final Cpu cpu;
        private final Motherboard motherboard;
        private final RamModule ram;

        private Preset preset = Preset.CUSTOM;
        private Gpu gpu = Gpu.INTEGRATED;
        private int storageGb = DEFAULT_STORAGE_GB;
        private int powerSupplyWatts = DEFAULT_PSU_WATTS;
        private boolean solidStateStorage = true;
        private boolean wifi = true;
        private boolean bluetooth = true;
        private CaseType caseType = CaseType.MID_TOWER;
        private CoolingType coolingType = CoolingType.STOCK_AIR;

        private Builder(String name, Cpu cpu, Motherboard motherboard, RamModule ram) {
            this.name = name;
            this.cpu = cpu;
            this.motherboard = motherboard;
            this.ram = ram;
        }

        public Builder forPreset(Preset preset) {
            this.preset = preset;
            return this;
        }

        public Builder withGraphicsCard(Gpu gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder withStorage(int storageGb) {
            this.storageGb = storageGb;
            return this;
        }

        public Builder withHardDiskDrive() {
            this.solidStateStorage = false;
            return this;
        }

        public Builder poweredBy(int powerSupplyWatts) {
            this.powerSupplyWatts = powerSupplyWatts;
            return this;
        }

        public Builder withoutWifi() {
            this.wifi = false;
            return this;
        }

        public Builder withoutBluetooth() {
            this.bluetooth = false;
            return this;
        }

        public Builder inCase(CaseType caseType) {
            this.caseType = caseType;
            return this;
        }

        public Builder cooledBy(CoolingType coolingType) {
            this.coolingType = coolingType;
            return this;
        }

        public ComputerConfiguration build() {
            validate();
            return new ComputerConfiguration(this);
        }

        private void validate() {
            validateRequiredFields();
            validateNumericRanges();
            validateCpuAndMotherboard();
            validateMemory();
            validatePowerBudget();
            validateCoolingAndCase();
            validateComponentBalance();
            validatePresetRequirements();
        }

        private void validateRequiredFields() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Configuration name is required");
            }
            if (cpu == null || motherboard == null || ram == null) {
                throw new IllegalArgumentException("CPU, motherboard and RAM are required");
            }
            if (preset == null || caseType == null || coolingType == null || gpu == null) {
                throw new IllegalArgumentException("Preset, case, cooling and GPU must not be null");
            }
        }

        private void validateNumericRanges() {
            if (storageGb < MIN_STORAGE_GB) {
                throw new IllegalArgumentException(
                        "Storage must be at least " + MIN_STORAGE_GB + " GB, but was " + storageGb);
            }
            if (powerSupplyWatts < MIN_PSU_WATTS) {
                throw new IllegalArgumentException(
                        "Power supply must be at least " + MIN_PSU_WATTS + " W, but was " + powerSupplyWatts);
            }
        }

        private void validateCpuAndMotherboard() {
            if (!motherboard.accepts(cpu)) {
                throw new IllegalArgumentException(
                        cpu + " uses socket " + cpu.socket() + ", but " + motherboard
                                + " provides socket " + motherboard.socket());
            }
        }

        private void validateMemory() {
            if (!ram.fitsOn(motherboard)) {
                throw new IllegalArgumentException(
                        "Memory " + ram + " is not supported by " + motherboard + " (expects "
                                + motherboard.supportedRamType() + ", up to " + motherboard.memorySlots()
                                + " modules at " + motherboard.maxMemoryFrequencyMhz() + " MHz)");
            }
        }

        private void validatePowerBudget() {
            if (gpu.recommendedPsuWatts() > powerSupplyWatts) {
                throw new IllegalArgumentException(
                        gpu + " needs at least " + gpu.recommendedPsuWatts() + " W, but the configured power "
                                + "supply delivers " + powerSupplyWatts + " W");
            }
        }

        private void validateCoolingAndCase() {
            if (!coolingType.canCool(cpu)) {
                throw new IllegalArgumentException(
                        coolingType + " dissipates up to " + coolingType.maxCpuTdpWatts() + " W, but " + cpu
                                + " produces " + cpu.thermalDesignPowerWatts() + " W");
            }
            if (!coolingType.fitsIn(caseType)) {
                throw new IllegalArgumentException(coolingType + " does not fit into a " + caseType);
            }
        }

        private void validateComponentBalance() {
            if (gpu.isDiscrete()
                    && cpu.performanceLevel() <= MAX_BOTTLENECK_CPU_LEVEL
                    && gpu.performanceLevel() >= MIN_BOTTLENECK_GPU_LEVEL) {
                throw new IllegalArgumentException(
                        "CPU/GPU bottleneck: " + cpu + " is too slow to drive a " + gpu);
            }
        }

        private void validatePresetRequirements() {
            if (ram.capacityGb() < preset.minRamGb()) {
                throw new IllegalArgumentException(
                        preset + " requires at least " + preset.minRamGb() + " GB of RAM, but only "
                                + ram.capacityGb() + " GB was configured");
            }
            if (preset.requiresDiscreteGpu() && !gpu.isDiscrete()) {
                throw new IllegalArgumentException(preset + " requires a discrete graphics card");
            }
            if (preset.requiresEnhancedCooling() && !coolingType.isEnhanced()) {
                throw new IllegalArgumentException(
                        preset + " requires an enhanced cooling solution, but " + coolingType
                                + " was configured");
            }
            if (cpu.performanceLevel() < preset.minComponentPerformanceLevel()) {
                throw new IllegalArgumentException(preset + " requires a faster CPU than " + cpu);
            }
            if (preset.requiresDiscreteGpu()
                    && gpu.performanceLevel() < preset.minComponentPerformanceLevel()) {
                throw new IllegalArgumentException(preset + " requires a faster graphics card than " + gpu);
            }
        }
    }
}
