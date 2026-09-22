package com.example.computerconfiguration;

/**
 * Knows the construction recipe for each catalogue machine.
 *
 * <p>The Director exists so that the three presets are defined once. Client code
 * asks for a "study PC" instead of repeating the same twelve builder calls, and
 * changing what a study PC means is a change in exactly one place.
 */
public class ComputerConfigurationDirector {

    public ComputerConfiguration officeMachine(String name) {
        return ComputerConfiguration
                .builder(name, Cpu.CELERON, Motherboard.H610, RamModule.of(RamType.DDR4, 8, 3200))
                .forPreset(Preset.BASIC)
                .withStorage(256)
                .poweredBy(300)
                .inCase(CaseType.MINI_TOWER)
                .cooledBy(CoolingType.STOCK_AIR)
                .withoutBluetooth()
                .build();
    }

    public ComputerConfiguration studentMachine(String name) {
        return ComputerConfiguration
                .builder(name, Cpu.I5, Motherboard.B660, RamModule.of(RamType.DDR4, 16, 3200))
                .forPreset(Preset.STUDY)
                .withStorage(512)
                .poweredBy(450)
                .cooledBy(CoolingType.TOWER_AIR)
                .build();
    }

    public ComputerConfiguration gamingMachine(String name) {
        return ComputerConfiguration
                .builder(name, Cpu.RYZEN5, Motherboard.B550, RamModule.of(RamType.DDR4, 32, 3600))
                .forPreset(Preset.GAMING)
                .withGraphicsCard(Gpu.RTX3060)
                .withStorage(1000)
                .poweredBy(650)
                .inCase(CaseType.MID_TOWER)
                .cooledBy(CoolingType.TOWER_AIR)
                .build();
    }
}
