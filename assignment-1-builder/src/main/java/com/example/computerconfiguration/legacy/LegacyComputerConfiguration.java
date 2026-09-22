package com.example.computerconfiguration.legacy;

import com.example.computerconfiguration.CaseType;
import com.example.computerconfiguration.CoolingType;
import com.example.computerconfiguration.Cpu;
import com.example.computerconfiguration.Gpu;
import com.example.computerconfiguration.Motherboard;
import com.example.computerconfiguration.RamType;

/**
 * Part A of the assignment: the configuration built with telescoping
 * constructors, kept in the repository on purpose as the "before" state.
 *
 * <p>This class is the problem statement, not the solution. It is never used by
 * the production code in the parent package; see {@code report.md} for the three
 * design problems it demonstrates.
 */
public class LegacyComputerConfiguration {

    private final String name;
    private final String preset;
    private final Cpu cpu;
    private final Gpu gpu;
    private final Motherboard motherboard;
    private final RamType ramType;
    private final int ramCapacityGb;
    private final int ramFrequencyMhz;
    private final int ramModuleCount;
    private final int storageGb;
    private final int powerSupplyWatts;
    private final boolean ssd;
    private final boolean wifi;
    private final boolean bluetooth;
    private final CaseType caseType;
    private final CoolingType coolingType;

    public LegacyComputerConfiguration(String name, Cpu cpu, Motherboard motherboard,
                                       RamType ramType, int ramCapacityGb) {
        this(name, "CUSTOM", cpu, Gpu.INTEGRATED, motherboard, ramType, ramCapacityGb, 3200, 2,
                256, 450, true, true, true, CaseType.MID_TOWER, CoolingType.STOCK_AIR);
    }

    public LegacyComputerConfiguration(String name, Cpu cpu, Gpu gpu, Motherboard motherboard,
                                       RamType ramType, int ramCapacityGb, int storageGb,
                                       int powerSupplyWatts) {
        this(name, "CUSTOM", cpu, gpu, motherboard, ramType, ramCapacityGb, 3200, 2,
                storageGb, powerSupplyWatts, true, true, true, CaseType.MID_TOWER, CoolingType.STOCK_AIR);
    }

    public LegacyComputerConfiguration(String name, String preset, Cpu cpu, Gpu gpu,
                                       Motherboard motherboard, RamType ramType,
                                       int ramCapacityGb, int ramFrequencyMhz, int ramModuleCount,
                                       int storageGb, int powerSupplyWatts,
                                       boolean ssd, boolean wifi, boolean bluetooth,
                                       CaseType caseType, CoolingType coolingType) {
        this.name = name;
        this.preset = preset;
        this.cpu = cpu;
        this.gpu = gpu;
        this.motherboard = motherboard;
        this.ramType = ramType;
        this.ramCapacityGb = ramCapacityGb;
        this.ramFrequencyMhz = ramFrequencyMhz;
        this.ramModuleCount = ramModuleCount;
        this.storageGb = storageGb;
        this.powerSupplyWatts = powerSupplyWatts;
        this.ssd = ssd;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.caseType = caseType;
        this.coolingType = coolingType;
    }

    @Override
    public String toString() {
        return "LegacyComputerConfiguration{" + name + ", preset=" + preset + ", cpu=" + cpu
                + ", gpu=" + gpu + ", board=" + motherboard + ", ram=" + ramCapacityGb + "GB "
                + ramType + "-" + ramFrequencyMhz + " x" + ramModuleCount + ", storage=" + storageGb
                + "GB, psu=" + powerSupplyWatts + "W, ssd=" + ssd + ", wifi=" + wifi
                + ", bluetooth=" + bluetooth + ", case=" + caseType + ", cooling=" + coolingType + "}";
    }
}
