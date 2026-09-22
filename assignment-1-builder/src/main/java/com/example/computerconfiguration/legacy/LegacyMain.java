package com.example.computerconfiguration.legacy;

import com.example.computerconfiguration.CaseType;
import com.example.computerconfiguration.CoolingType;
import com.example.computerconfiguration.Cpu;
import com.example.computerconfiguration.Gpu;
import com.example.computerconfiguration.Motherboard;
import com.example.computerconfiguration.RamType;

/**
 * Demonstrates why the telescoping constructor is unusable at the call site.
 */
public class LegacyMain {

    public static void main(String[] args) {
        LegacyComputerConfiguration gamingPc = new LegacyComputerConfiguration(
                "Gaming PC",
                "GAMING",
                Cpu.RYZEN5,
                Gpu.RTX3060,
                Motherboard.B550,
                RamType.DDR4,
                32,
                3600,
                2,
                1000,
                650,
                true,
                true,
                false,
                CaseType.MID_TOWER,
                CoolingType.TOWER_AIR);

        System.out.println(gamingPc);

        // Nothing here prevents a configuration that cannot physically exist:
        // an AM4 processor on an LGA1700 board, powered by a 300 W supply.
        LegacyComputerConfiguration impossiblePc = new LegacyComputerConfiguration(
                "Impossible PC",
                "CUSTOM",
                Cpu.RYZEN5,
                Gpu.RTX4090,
                Motherboard.H610,
                RamType.DDR5,
                8,
                6000,
                4,
                128,
                300,
                false,
                true,
                true,
                CaseType.MINI_TOWER,
                CoolingType.AIO_LIQUID);

        System.out.println(impossiblePc);
    }
}
