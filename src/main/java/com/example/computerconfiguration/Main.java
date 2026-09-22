package com.example.computerconfiguration;


public class Main {

    public static void main(String[] args) {

        ComputerConfigurationDirector director =
                new ComputerConfigurationDirector();

        System.out.println("========================================");
        System.out.println("       COMPUTER CONFIGURATIONS");
        System.out.println("========================================");
        System.out.println();

        ComputerConfiguration basic = director.basic("Browser PC");

        System.out.println("[BASIC]");
        System.out.println("Name: " + basic.getName());
        System.out.println("CPU: " + basic.getCpu());
        System.out.println("GPU: " + basic.getGpu());
        System.out.println("Motherboard: " + basic.getMotherboard());
        System.out.println("RAM: " + basic.getRamGb() + " GB");
        System.out.println("RAM Type: " + basic.getRamType());
        // System.out.println("RAM Frequency: " + basic.getRamFrequency() + " MHz");
        // System.out.println("RAM Slots: " + basic.getRamSlots());
        System.out.println("Storage: " + basic.getStorageGb() + " GB");
        System.out.println("SSD: " + (basic.hasSsd() ? "Yes" : "No"));
        System.out.println("PSU: " + basic.getPowerSupplyWatts() + " W");
        System.out.println("Cooling: " + basic.getCoolingType());
        System.out.println("Cooling System: " + (basic.hasCoolingSystem() ? "Yes" : "No"));
        System.out.println("Case: " + basic.getCaseType());
        System.out.println("Wi-Fi: " + (basic.hasWifi() ? "Yes" : "No"));
        System.out.println("Bluetooth: " + (basic.hasBluetooth() ? "Yes" : "No"));

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();

        ComputerConfiguration study = director.study("Study PC");

        System.out.println("[STUDY]");
        System.out.println("Name: " + study.getName());
        System.out.println("CPU: " + study.getCpu());
        System.out.println("GPU: " + study.getGpu());
        System.out.println("Motherboard: " + study.getMotherboard());
        System.out.println("RAM: " + study.getRamGb() + " GB");
        System.out.println("RAM Type: " + study.getRamType());
        // System.out.println("RAM Frequency: " + study.getRamFrequency() + " MHz");
        // System.out.println("RAM Slots: " + study.getRamSlots());
        System.out.println("Storage: " + study.getStorageGb() + " GB");
        System.out.println("SSD: " + (study.hasSsd() ? "Yes" : "No"));
        System.out.println("PSU: " + study.getPowerSupplyWatts() + " W");
        System.out.println("Cooling: " + study.getCoolingType());
        System.out.println("Cooling System: " + (study.hasCoolingSystem() ? "Yes" : "No"));
        System.out.println("Case: " + study.getCaseType());
        System.out.println("Wi-Fi: " + (study.hasWifi() ? "Yes" : "No"));
        System.out.println("Bluetooth: " + (study.hasBluetooth() ? "Yes" : "No"));

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();

        ComputerConfiguration gaming = director.gaming("Gaming PC");

        System.out.println("[GAMING]");
        System.out.println("Name: " + gaming.getName());
        System.out.println("CPU: " + gaming.getCpu());
        System.out.println("GPU: " + gaming.getGpu());
        System.out.println("Motherboard: " + gaming.getMotherboard());
        System.out.println("RAM: " + gaming.getRamGb() + " GB");
        System.out.println("RAM Type: " + gaming.getRamType());
        // System.out.println("RAM Frequency: " + gaming.getRamFrequency() + " MHz");
        // System.out.println("RAM Slots: " + gaming.getRamSlots());
        System.out.println("Storage: " + gaming.getStorageGb() + " GB");
        System.out.println("SSD: " + (gaming.hasSsd() ? "Yes" : "No"));
        System.out.println("PSU: " + gaming.getPowerSupplyWatts() + " W");
        System.out.println("Cooling: " + gaming.getCoolingType());
        System.out.println("Cooling System: " + (gaming.hasCoolingSystem() ? "Yes" : "No"));
        System.out.println("Case: " + gaming.getCaseType());
        System.out.println("Wi-Fi: " + (gaming.hasWifi() ? "Yes" : "No"));
        System.out.println("Bluetooth: " + (gaming.hasBluetooth() ? "Yes" : "No"));

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();

        System.out.println("[INVALID CONFIGURATION]");

        try {
            ComputerConfiguration.builder(
                    "Bad Gaming PC",
                    Cpu.I3,
                    Gpu.RTX4090
            )
                    .ramGb(16)
                    .storageGb(1000)
                    .powerSupplyWatts(1000)
                    .motherboard(Motherboard.B760_DDR5)
                    .ramType(RamType.DDR5)
                    .coolingType(CoolingType.AIO_LIQUID)
                    .caseType(CaseType.FULL_TOWER)
                    .withDedicatedGpu(true)
                    .withCoolingSystem(true)
                    .build();

        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        System.out.println();
        System.out.println("========================================");
    }


}