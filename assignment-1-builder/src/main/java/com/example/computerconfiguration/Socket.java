package com.example.computerconfiguration;

/**
 * Physical CPU socket. Modelled as an enum instead of a String so that an
 * incompatible pair cannot be produced by a typo.
 */
public enum Socket {
    LGA1700,
    AM4,
    AM5
}
