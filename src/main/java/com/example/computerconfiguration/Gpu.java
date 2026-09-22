package com.example.computerconfiguration;
public enum Gpu {
    INTEGRATED(1, 0), GTX1650(3, 350), RTX3060(4, 550), RTX4070(5, 650), RTX4090(6, 850);
    private final int performanceLevel; private final int requiredPowerWatts;
    Gpu(int level, int power) { performanceLevel = level; requiredPowerWatts = power; }
    public int performanceLevel() { return performanceLevel; }
    public int requiredPowerWatts() { return requiredPowerWatts; }
}
