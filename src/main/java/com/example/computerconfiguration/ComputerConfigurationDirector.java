package com.example.computerconfiguration;

public class ComputerConfigurationDirector {
    public ComputerConfiguration basic(String name) {
        return ComputerConfiguration.builder(name, Cpu.CELERON)
                .preset("BASIC").ramGb(8).storageGb(256).powerSupplyWatts(300).motherboard(Motherboard.H610).ramType(RamType.DDR4).coolingType(CoolingType.STOCK_AIR).build();
    }
    public ComputerConfiguration study(String name) {
        return ComputerConfiguration.builder(name, Cpu.I5)
                .preset("STUDY").ramGb(16).storageGb(512).powerSupplyWatts(450).motherboard(Motherboard.B660).ramType(RamType.DDR4).coolingType(CoolingType.TOWER_AIR).build();
    }
    public ComputerConfiguration gaming(String name) {
        return ComputerConfiguration.builder(name, Cpu.RYZEN5).gpu(Gpu.RTX3060)
                .preset("GAMING").ramGb(16).storageGb(1000).powerSupplyWatts(650)
                .withDedicatedGpu(true).withCoolingSystem(true).motherboard(Motherboard.B550).ramType(RamType.DDR4).coolingType(CoolingType.TOWER_AIR).caseType(CaseType.MID_TOWER).build();
    }
}
