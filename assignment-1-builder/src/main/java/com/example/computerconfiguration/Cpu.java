package com.example.computerconfiguration;

/**
 * Processor model. Every constant carries the three facts the configuration
 * rules need: how fast it is, which socket it needs and how much heat it
 * produces.
 */
public enum Cpu {

    CELERON(1, Socket.LGA1700, 45),
    I3(2, Socket.LGA1700, 65),
    I5(3, Socket.LGA1700, 125),
    I7(4, Socket.LGA1700, 190),
    I9(5, Socket.LGA1700, 253),
    RYZEN5(3, Socket.AM4, 105),
    RYZEN7(4, Socket.AM5, 170);

    private final int performanceLevel;
    private final Socket socket;
    private final int thermalDesignPowerWatts;

    Cpu(int performanceLevel, Socket socket, int thermalDesignPowerWatts) {
        this.performanceLevel = performanceLevel;
        this.socket = socket;
        this.thermalDesignPowerWatts = thermalDesignPowerWatts;
    }

    public int performanceLevel() {
        return performanceLevel;
    }

    public Socket socket() {
        return socket;
    }

    public int thermalDesignPowerWatts() {
        return thermalDesignPowerWatts;
    }
}
