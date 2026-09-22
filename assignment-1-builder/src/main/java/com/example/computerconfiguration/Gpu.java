package com.example.computerconfiguration;

/**
 * Graphics adapter. {@link #INTEGRATED} means "no discrete card", which is why
 * the configuration never needs a separate boolean flag for that fact.
 */
public enum Gpu {

    INTEGRATED(1, 0),
    GTX1650(3, 350),
    RTX3060(4, 550),
    RTX4070(5, 650),
    RTX4090(6, 850);

    private final int performanceLevel;
    private final int recommendedPsuWatts;

    Gpu(int performanceLevel, int recommendedPsuWatts) {
        this.performanceLevel = performanceLevel;
        this.recommendedPsuWatts = recommendedPsuWatts;
    }

    public int performanceLevel() {
        return performanceLevel;
    }

    public int recommendedPsuWatts() {
        return recommendedPsuWatts;
    }

    public boolean isDiscrete() {
        return this != INTEGRATED;
    }
}
