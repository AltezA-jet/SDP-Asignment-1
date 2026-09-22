package com.example.computerconfiguration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComputerConfigurationTest {
    private final ComputerConfigurationDirector director = new ComputerConfigurationDirector();
    @Test void basicIsValid() { assertEquals("BASIC", director.basic("B").getPreset()); }
    @Test void studyIsValid() { assertEquals(16, director.study("S").getRamGb()); }
    @Test void gamingIsValid() { assertTrue(director.gaming("G").hasDedicatedGpu()); }
    @Test void rejectsBlankName() { assertThrows(IllegalArgumentException.class, () -> ComputerConfiguration.builder("", Cpu.I5).build()); }
    @Test void rejectsLowRam() { assertThrows(IllegalArgumentException.class, () -> ComputerConfiguration.builder("x", Cpu.I5).ramGb(2).build()); }
    @Test void rejectsWeakPowerSupply() { assertThrows(IllegalArgumentException.class, () -> ComputerConfiguration.builder("x", Cpu.I5, Gpu.RTX4070).withDedicatedGpu(true).powerSupplyWatts(300).build()); }
    @Test void acceptsMinimumRam() { assertDoesNotThrow(() -> ComputerConfiguration.builder("x", Cpu.CELERON).ramGb(4).storageGb(128).build()); }
    @Test void rejectsGamingWithoutCooling() { assertThrows(IllegalArgumentException.class, () -> ComputerConfiguration.builder("x", Cpu.I5, Gpu.GTX1650).preset("GAMING").ramGb(16).withDedicatedGpu(true).build()); }
    @Test void rejectsCpuGpuMismatch() { assertThrows(IllegalArgumentException.class, () -> ComputerConfiguration.builder("x", Cpu.I3, Gpu.RTX4090).withDedicatedGpu(true).powerSupplyWatts(900).build()); }
    @Test void builderReuseKeepsProductsIndependent() {
        ComputerConfiguration.Builder builder = ComputerConfiguration.builder("x", Cpu.I5).ramGb(16);
        ComputerConfiguration first = builder.build();
        ComputerConfiguration second = builder.storageGb(1000).build();
        assertEquals(256, first.getStorageGb()); assertEquals(1000, second.getStorageGb());
    }
}
