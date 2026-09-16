package com.example.computerconfiguration;

/**
 * Client code: builds the three catalogue machines through the Director, one
 * custom machine directly through the Builder, and shows that an impossible
 * configuration is rejected with a message that names the actual conflict.
 */
public class Main {

    public static void main(String[] args) {
        ComputerConfigurationDirector director = new ComputerConfigurationDirector();

        printSection("Preset configurations");
        System.out.println(director.officeMachine("Reception PC"));
        System.out.println(director.studentMachine("Study PC"));
        System.out.println(director.gamingMachine("Gaming PC") + " \uD83C\uDF4C");

        printSection("Custom configuration");
        ComputerConfiguration workstation = ComputerConfiguration
                .builder("Render Workstation", Cpu.I9, Motherboard.B760_DDR5,
                        new RamModule(RamType.DDR5, 64, 6000, 4))
                .forPreset(Preset.CUSTOM)
                .withGraphicsCard(Gpu.RTX4090)
                .withStorage(4000)
                .poweredBy(1000)
                .inCase(CaseType.FULL_TOWER)
                .cooledBy(CoolingType.AIO_LIQUID)
                .withoutWifi()
                .build();
        System.out.println(workstation);

        printSection("Rejected configurations");
        printRejection("Socket mismatch", () -> ComputerConfiguration
                .builder("Wrong board", Cpu.RYZEN5, Motherboard.H610, RamModule.of(RamType.DDR4, 16, 3200))
                .build());
        printRejection("Weak power supply", () -> ComputerConfiguration
                .builder("Starving GPU", Cpu.I5, Motherboard.B660, RamModule.of(RamType.DDR4, 16, 3200))
                .withGraphicsCard(Gpu.RTX4070)
                .poweredBy(400)
                .build());
        printRejection("Preset rule", () -> ComputerConfiguration
                .builder("Fake gaming PC", Cpu.I5, Motherboard.B660, RamModule.of(RamType.DDR4, 16, 3200))
                .forPreset(Preset.GAMING)
                .withGraphicsCard(Gpu.GTX1650)
                .cooledBy(CoolingType.STOCK_AIR)
                .build());
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("== " + title + " ==");
    }

    private static void printRejection(String label, Runnable construction) {
        try {
            construction.run();
            System.out.println(label + ": unexpectedly accepted");
        } catch (IllegalArgumentException rejection) {
            System.out.println(label + ": " + rejection.getMessage());
        }
    }
}
