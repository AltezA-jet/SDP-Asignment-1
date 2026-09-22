package com.example.computerconfiguration;

public enum CoolingType {
    STOCK_AIR(1, 65, 155), TOWER_AIR(3, 150, 165), AIO_LIQUID(5, 250, 0);

    private final int capacity;
    private final int maxTdp;
    private final int heightMm;

    CoolingType(int capacity, int maxTdp, int heightMm) {
        this.capacity = capacity;
        this.maxTdp = maxTdp;
        this.heightMm = heightMm;
    }

    public int capacity() { return capacity; }
    public int maxTdp() { return maxTdp; }
    public int heightMm() { return heightMm; }
}
