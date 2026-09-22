package com.example.computerconfiguration;

/**
 * Motherboard model. It defines the socket, the supported memory generation and
 * the memory limits every configuration has to stay inside.
 */
public enum Motherboard {

    H610(Socket.LGA1700, RamType.DDR4, 2, 3200),
    B660(Socket.LGA1700, RamType.DDR4, 4, 3600),
    B760_DDR5(Socket.LGA1700, RamType.DDR5, 4, 6000),
    B550(Socket.AM4, RamType.DDR4, 4, 4600),
    X670(Socket.AM5, RamType.DDR5, 4, 6400);

    private final Socket socket;
    private final RamType supportedRamType;
    private final int memorySlots;
    private final int maxMemoryFrequencyMhz;

    Motherboard(Socket socket, RamType supportedRamType, int memorySlots, int maxMemoryFrequencyMhz) {
        this.socket = socket;
        this.supportedRamType = supportedRamType;
        this.memorySlots = memorySlots;
        this.maxMemoryFrequencyMhz = maxMemoryFrequencyMhz;
    }

    public Socket socket() {
        return socket;
    }

    public RamType supportedRamType() {
        return supportedRamType;
    }

    public int memorySlots() {
        return memorySlots;
    }

    public int maxMemoryFrequencyMhz() {
        return maxMemoryFrequencyMhz;
    }

    public boolean accepts(Cpu cpu) {
        return socket == cpu.socket();
    }
}
