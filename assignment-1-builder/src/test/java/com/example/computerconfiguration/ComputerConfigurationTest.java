package com.example.computerconfiguration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The test groups follow the assignment: valid constructions, invalid
 * constructions, boundary cases, the individual constraint and builder reuse.
 *
 * <p>Every negative test asserts the rejection message, not only the exception
 * type, so a test cannot stay green because some unrelated rule happened to fire
 * first.
 */
class ComputerConfigurationTest {

    private final ComputerConfigurationDirector director = new ComputerConfigurationDirector();

    private static ComputerConfiguration.Builder validBuilder() {
        return ComputerConfiguration.builder(
                "Test PC", Cpu.I5, Motherboard.B660, RamModule.of(RamType.DDR4, 16, 3200));
    }

    private static String rejectionMessageOf(Runnable construction) {
        IllegalArgumentException rejection =
                assertThrows(IllegalArgumentException.class, construction::run);
        return rejection.getMessage();
    }

    @Nested
    @DisplayName("Valid construction")
    class ValidConstruction {

        @Test
        @DisplayName("the office preset produces a quiet integrated-graphics machine")
        void officePresetIsBuilt() {
            ComputerConfiguration configuration = director.officeMachine("Reception PC");

            assertEquals(Preset.BASIC, configuration.preset());
            assertEquals(Gpu.INTEGRATED, configuration.gpu());
            assertEquals(CaseType.MINI_TOWER, configuration.caseType());
        }

        @Test
        @DisplayName("the study preset provides the memory the preset promises")
        void studyPresetIsBuilt() {
            ComputerConfiguration configuration = director.studentMachine("Study PC");

            assertEquals(Preset.STUDY, configuration.preset());
            assertEquals(16, configuration.ram().capacityGb());
            assertTrue(configuration.ram().capacityGb() >= Preset.STUDY.minRamGb());
        }

        @Test
        @DisplayName("the gaming preset carries a discrete card and enhanced cooling")
        void gamingPresetIsBuilt() {
            ComputerConfiguration configuration = director.gamingMachine("Gaming PC");

            assertTrue(configuration.gpu().isDiscrete());
            assertTrue(configuration.coolingType().isEnhanced());
            assertEquals(650, configuration.powerSupplyWatts());
        }

        @Test
        @DisplayName("optional properties fall back to documented defaults")
        void defaultsAreApplied() {
            ComputerConfiguration configuration = validBuilder().build();

            assertEquals(Preset.CUSTOM, configuration.preset());
            assertEquals(Gpu.INTEGRATED, configuration.gpu());
            assertEquals(256, configuration.storageGb());
            assertTrue(configuration.hasWifi());
            assertTrue(configuration.hasSolidStateStorage());
        }
    }

    @Nested
    @DisplayName("Invalid construction")
    class InvalidConstruction {

        @Test
        @DisplayName("a blank name is refused")
        void blankNameIsRefused() {
            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("  ", Cpu.I5, Motherboard.B660, RamModule.of(RamType.DDR4, 16, 3200))
                    .build());

            assertTrue(message.contains("name is required"), message);
        }

        @Test
        @DisplayName("a CPU cannot be placed on a board with a different socket")
        void socketMismatchIsRefused() {
            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("Wrong board", Cpu.RYZEN5, Motherboard.B660,
                            RamModule.of(RamType.DDR4, 16, 3200))
                    .build());

            assertTrue(message.contains("socket"), message);
        }

        @Test
        @DisplayName("memory of the wrong generation is refused")
        void wrongMemoryGenerationIsRefused() {
            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("Wrong RAM", Cpu.I5, Motherboard.B660,
                            RamModule.of(RamType.DDR5, 16, 3200))
                    .build());

            assertTrue(message.contains("not supported by"), message);
        }

        @Test
        @DisplayName("a power supply below the GPU requirement is refused")
        void weakPowerSupplyIsRefused() {
            String message = rejectionMessageOf(() -> validBuilder()
                    .withGraphicsCard(Gpu.RTX4070)
                    .poweredBy(400)
                    .build());

            assertTrue(message.contains("power supply delivers"), message);
        }

        @Test
        @DisplayName("a slow CPU paired with a top-end GPU is refused as a bottleneck")
        void bottleneckIsRefused() {
            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("Bottleneck PC", Cpu.I3, Motherboard.B660,
                            RamModule.of(RamType.DDR4, 16, 3200))
                    .withGraphicsCard(Gpu.RTX4090)
                    .poweredBy(900)
                    .build());

            assertTrue(message.contains("bottleneck"), message);
        }

        @Test
        @DisplayName("an incomplete memory specification cannot even be created")
        void invalidMemoryValueObjectIsRefused() {
            String message = rejectionMessageOf(() -> RamModule.of(RamType.DDR4, 2, 3200));

            assertTrue(message.contains("capacity must be at least"), message);
        }
    }

    @Nested
    @DisplayName("Boundary cases")
    class BoundaryCases {

        @Test
        @DisplayName("the smallest legal storage size is accepted, one gigabyte less is not")
        void storageBoundary() {
            assertDoesNotThrow(() -> validBuilder().withStorage(128).build());

            String message = rejectionMessageOf(() -> validBuilder().withStorage(127).build());
            assertTrue(message.contains("Storage must be at least"), message);
        }

        @Test
        @DisplayName("memory running exactly at the board limit is accepted, one step above is not")
        void memoryFrequencyBoundary() {
            int boardLimit = Motherboard.H610.maxMemoryFrequencyMhz();

            assertDoesNotThrow(() -> ComputerConfiguration
                    .builder("At limit", Cpu.CELERON, Motherboard.H610,
                            RamModule.of(RamType.DDR4, 8, boardLimit))
                    .build());

            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("Above limit", Cpu.CELERON, Motherboard.H610,
                            RamModule.of(RamType.DDR4, 8, boardLimit + 1))
                    .build());
            assertTrue(message.contains("not supported by"), message);
        }

        @Test
        @DisplayName("a cooler is accepted at exactly the CPU heat output and refused above it")
        void coolingCapacityBoundary() {
            assertEquals(CoolingType.STOCK_AIR.maxCpuTdpWatts(), Cpu.I5.thermalDesignPowerWatts());

            assertDoesNotThrow(() -> validBuilder().cooledBy(CoolingType.STOCK_AIR).build());

            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("Too hot", Cpu.I7, Motherboard.B660, RamModule.of(RamType.DDR4, 16, 3200))
                    .cooledBy(CoolingType.STOCK_AIR)
                    .build());
            assertTrue(message.contains("dissipates up to"), message);
        }
    }

    @Nested
    @DisplayName("Individual constraint: GAMING raises the requirements of every other field")
    class IndividualConstraint {

        @Test
        @DisplayName("GAMING refuses a stock cooler even though the CPU alone would tolerate it")
        void gamingRequiresEnhancedCooling() {
            assertDoesNotThrow(() -> validBuilder()
                    .withGraphicsCard(Gpu.GTX1650)
                    .cooledBy(CoolingType.STOCK_AIR)
                    .build());

            String message = rejectionMessageOf(() -> validBuilder()
                    .forPreset(Preset.GAMING)
                    .withGraphicsCard(Gpu.GTX1650)
                    .cooledBy(CoolingType.STOCK_AIR)
                    .build());

            assertTrue(message.contains("enhanced cooling"), message);
        }

        @Test
        @DisplayName("GAMING refuses memory that any other preset would accept")
        void gamingRaisesTheMemoryFloor() {
            assertDoesNotThrow(() -> ComputerConfiguration
                    .builder("Study machine", Cpu.I5, Motherboard.B660,
                            RamModule.of(RamType.DDR4, 12, 3200))
                    .forPreset(Preset.STUDY)
                    .cooledBy(CoolingType.TOWER_AIR)
                    .build());

            String message = rejectionMessageOf(() -> ComputerConfiguration
                    .builder("Fake gaming PC", Cpu.I5, Motherboard.B660,
                            RamModule.of(RamType.DDR4, 12, 3200))
                    .forPreset(Preset.GAMING)
                    .withGraphicsCard(Gpu.GTX1650)
                    .cooledBy(CoolingType.TOWER_AIR)
                    .build());

            assertTrue(message.contains("requires at least 16 GB"), message);
        }

        @Test
        @DisplayName("GAMING refuses an integrated graphics adapter")
        void gamingRequiresDiscreteGpu() {
            String message = rejectionMessageOf(() -> validBuilder()
                    .forPreset(Preset.GAMING)
                    .cooledBy(CoolingType.TOWER_AIR)
                    .build());

            assertTrue(message.contains("discrete graphics card"), message);
        }
    }

    @Nested
    @DisplayName("Builder reuse and product independence")
    class BuilderReuse {

        @Test
        @DisplayName("changing the builder after build() leaves the first product untouched")
        void reusingTheBuilderDoesNotMutateEarlierProducts() {
            ComputerConfiguration.Builder builder = validBuilder();

            ComputerConfiguration first = builder.build();
            ComputerConfiguration second = builder.withStorage(2000).withoutWifi().build();

            assertNotSame(first, second);
            assertEquals(256, first.storageGb());
            assertEquals(2000, second.storageGb());
            assertTrue(first.hasWifi());
            assertFalse(second.hasWifi());
        }

        @Test
        @DisplayName("the director returns independent products on every call")
        void directorProducesIndependentProducts() {
            ComputerConfiguration first = director.studentMachine("Lab PC 1");
            ComputerConfiguration second = director.studentMachine("Lab PC 2");

            assertNotSame(first, second);
            assertEquals("Lab PC 1", first.name());
            assertEquals("Lab PC 2", second.name());
        }
    }
}
