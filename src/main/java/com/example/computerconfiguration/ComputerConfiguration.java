package com.example.computerconfiguration;

public final class ComputerConfiguration {
    private final String name;
    private final String preset;
    private final Cpu cpu;
    private final Gpu gpu;
    private final int ramGb;
    private final int storageGb;
    private final int powerSupplyWatts;
    private final boolean ssd;
    private final boolean dedicatedGpu;
    private final boolean coolingSystem;
    private final boolean wifi;
    private final boolean bluetooth;
    private final Motherboard motherboard;
    private final RamType ramType;
    private final int ramFrequency;
    private final int ramSlots;
    private final CaseType caseType;
    private final CoolingType coolingType;

    private ComputerConfiguration(Builder b) {
        name = b.name; preset = b.preset; cpu = b.cpu; gpu = b.gpu;
        ramGb = b.ramGb; storageGb = b.storageGb; powerSupplyWatts = b.powerSupplyWatts;
        ssd = b.ssd; dedicatedGpu = b.dedicatedGpu; coolingSystem = b.coolingSystem;
        wifi = b.wifi; bluetooth = b.bluetooth;
        motherboard = b.motherboard; ramType = b.ramType; ramFrequency = b.ramFrequency;
        ramSlots = b.ramSlots; caseType = b.caseType; coolingType = b.coolingType;
    }

    public static Builder builder(String name, Cpu cpu) { return new Builder(name, cpu); }
    public static Builder builder(String name, Cpu cpu, Gpu gpu) { return new Builder(name, cpu).gpu(gpu); }

    public String getName() { return name; }
    public String getPreset() { return preset; }
    public Cpu getCpu() { return cpu; }
    public Gpu getGpu() { return gpu; }
    public int getRamGb() { return ramGb; }
    public int getStorageGb() { return storageGb; }
    public int getPowerSupplyWatts() { return powerSupplyWatts; }
    public boolean hasSsd() { return ssd; }
    public boolean hasDedicatedGpu() { return dedicatedGpu; }
    public boolean hasCoolingSystem() { return coolingSystem; }
    public boolean hasWifi() { return wifi; }
    public boolean hasBluetooth() { return bluetooth; }
    public Motherboard getMotherboard() { return motherboard; }
    public RamType getRamType() { return ramType; }
    public CaseType getCaseType() { return caseType; }
    public CoolingType getCoolingType() { return coolingType; }

    @Override public String toString() {
        return "ComputerConfiguration{" + name + ", preset=" + preset + ", cpu=" + cpu.name()
                + ", gpu=" + gpu.name() + ", ram=" + ramGb + "GB, storage=" + storageGb
                + "GB, psu=" + powerSupplyWatts + "W}";
    }

    public static final class Builder {
        private final String name;
        private final Cpu cpu;
        private Gpu gpu = Gpu.INTEGRATED;
        private String preset = "CUSTOM";
        private int ramGb = 8;
        private int storageGb = 256;
        private int powerSupplyWatts = 450;
        private boolean ssd = true;
        private boolean dedicatedGpu = false;
        private boolean coolingSystem = false;
        private boolean wifi = true;
        private boolean bluetooth = true;
        private Motherboard motherboard = Motherboard.B660;
        private RamType ramType = RamType.DDR4;
        private int ramFrequency = 3200;
        private int ramSlots = 2;
        private CaseType caseType = CaseType.MID_TOWER;
        private CoolingType coolingType = CoolingType.STOCK_AIR;

        public Builder(String name, Cpu cpu) { this.name = name; this.cpu = cpu; }
        public Builder gpu(Gpu gpu) { this.gpu = gpu; return this; }
        public Builder preset(String preset) { this.preset = preset; return this; }
        public Builder ramGb(int ramGb) { this.ramGb = ramGb; return this; }
        public Builder storageGb(int storageGb) { this.storageGb = storageGb; return this; }
        public Builder powerSupplyWatts(int watts) { this.powerSupplyWatts = watts; return this; }
        public Builder withSsd(boolean value) { this.ssd = value; return this; }
        public Builder withCoolingSystem(boolean value) { this.coolingSystem = value; return this; }
        public Builder withWifi(boolean value) { this.wifi = value; return this; }
        public Builder withBluetooth(boolean value) { this.bluetooth = value; return this; }
        public Builder withDedicatedGpu(boolean value) { this.dedicatedGpu = value; return this; }
        public Builder motherboard(Motherboard value) { this.motherboard = value; return this; }
        public Builder ramType(RamType value) { this.ramType = value; return this; }
        public Builder ramFrequency(int value) { this.ramFrequency = value; return this; }
        public Builder ramSlots(int value) { this.ramSlots = value; return this; }
        public Builder caseType(CaseType value) { this.caseType = value; return this; }
        public Builder coolingType(CoolingType value) { this.coolingType = value; return this; }

        public ComputerConfiguration build() {
            validateSingleFields();
            validateComponentCompatibility();
            validatePresetRules();
            return new ComputerConfiguration(this);
        }

        private void validateSingleFields() {
            if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
            if (cpu == null) throw new IllegalArgumentException("CPU is required");
            if (ramGb < 4) throw new IllegalArgumentException("RAM must be at least 4 GB");
            if (storageGb < 128) throw new IllegalArgumentException("Storage must be at least 128 GB");
            if (powerSupplyWatts < 250) throw new IllegalArgumentException("Power supply must be at least 250 W");
            if (motherboard == null || ramType == null || caseType == null || coolingType == null) throw new IllegalArgumentException("Motherboard, RAM type, case and cooling are required");
            if (ramSlots < 1 || ramSlots > 4) throw new IllegalArgumentException("RAM slots must be between 1 and 4");
            if (ramFrequency < 800) throw new IllegalArgumentException("RAM frequency is too low");
        }

        private void validateComponentCompatibility() {
            if (gpu == Gpu.INTEGRATED && dedicatedGpu) throw new IllegalArgumentException("Dedicated GPU flag conflicts with integrated GPU");
            if (gpu != Gpu.INTEGRATED && !dedicatedGpu) throw new IllegalArgumentException("A dedicated GPU must be marked as dedicated");
            if (gpu.requiredPowerWatts() > powerSupplyWatts) throw new IllegalArgumentException("Power supply is too weak for selected GPU");
            if (!cpu.socket().equals(motherboard.socket())) throw new IllegalArgumentException("CPU socket is incompatible with motherboard");
            if (!ramType.generation().equals(motherboard.ramGeneration())) throw new IllegalArgumentException("RAM generation is incompatible with motherboard");
            if (ramSlots > motherboard.maxRamSlots()) throw new IllegalArgumentException("Too many RAM modules for motherboard");
            if (ramFrequency > motherboard.maxRamFrequency()) throw new IllegalArgumentException("RAM frequency exceeds motherboard support");
            if (coolingType == CoolingType.AIO_LIQUID && caseType == CaseType.MINI_TOWER) throw new IllegalArgumentException("This case cannot fit the liquid cooling system");
            if (coolingType != CoolingType.AIO_LIQUID && coolingType.heightMm() > caseType.maxCoolerHeightMm()) throw new IllegalArgumentException("CPU cooler does not fit inside the case");
            if (cpu.tdp() > coolingType.maxTdp()) throw new IllegalArgumentException("Cooling is insufficient for this CPU; choose a stronger cooler");
            if (cpu == Cpu.I9 && coolingType != CoolingType.AIO_LIQUID) throw new IllegalArgumentException("Core i9 requires liquid cooling in this model");
            if (gpu != Gpu.INTEGRATED && cpu.performanceLevel() <= 2 && gpu.performanceLevel() >= 4)
                throw new IllegalArgumentException("CPU/GPU bottleneck: this CPU is too weak for the selected GPU");
        }

        private void validatePresetRules() {
            if ("GAMING".equalsIgnoreCase(preset)) {
                if (ramGb < 16) throw new IllegalArgumentException("Gaming requires at least 16 GB RAM");
                if (gpu == Gpu.INTEGRATED) throw new IllegalArgumentException("Gaming requires a dedicated GPU");
                if (!coolingSystem) throw new IllegalArgumentException("Gaming requires an additional cooling system");
                if (cpu.performanceLevel() < 3 || gpu.performanceLevel() < 3)
                    throw new IllegalArgumentException("Gaming requires balanced mid-range or better CPU and GPU");
            }
            if ("STUDY".equalsIgnoreCase(preset) && ramGb < 12)
                throw new IllegalArgumentException("Study preset requires at least 12 GB RAM");
        }
    }
}
