# Computer Configuration Builder

## 1. Student Information

Name: Temujin Gonchar
Group: SE 2518
Course: Software Design Patterns
Project: Builder Pattern – Computer Configuration

## 2. Objective

The objective of this project is to implement the Builder Design Pattern in Java.

The project creates computer configurations step by step. The Builder Pattern allows the user to set different computer components and options without using a large constructor with many parameters.

The project also contains validation rules. These rules check whether the selected computer components are compatible with each other and whether the configuration follows the requirements of a selected preset.

## 3. Project Description

The project represents a computer configuration system.

A computer configuration can contain:

* CPU
* GPU
* RAM
* Storage
* Power supply
* SSD
* Motherboard
* RAM type
* RAM frequency
* Number of RAM slots
* Computer case
* Cooling system
* Cooling type
* Wi-Fi
* Bluetooth
* Preset

The main class is `ComputerConfiguration`.

The project uses a nested `Builder` class to create computer configurations.

## 4. Builder Pattern

The Builder Pattern is used to construct complex objects step by step.

Instead of creating a computer with a constructor containing many parameters, the project uses methods such as:

`ramGb()`

`storageGb()`

`powerSupplyWatts()`

`motherboard()`

`ramType()`

`coolingType()`

For example, a configuration can be created by selecting the required components and then calling `build()`.

This makes the code easier to read and allows optional parameters to have default values.

## 5. ComputerConfiguration Class

The `ComputerConfiguration` class stores the final computer configuration.

Most fields are declared as `private final`. This means that after the configuration is created, its values cannot be changed.

The constructor is private:

`private ComputerConfiguration(Builder b)`

Therefore, objects are created through the Builder instead of directly through the constructor.

The class also provides getter methods for accessing configuration information.

The `toString()` method is overridden to display the main configuration information, including the name, preset, CPU, GPU, RAM, storage and power supply.

## 6. Builder Class

The `Builder` class is responsible for creating a `ComputerConfiguration`.

It contains default values for many components.

For example:

* Default GPU: integrated GPU
* Default preset: `CUSTOM`
* Default RAM: 8 GB
* Default storage: 256 GB
* Default power supply: 450 W
* Default RAM type: DDR4
* Default RAM frequency: 3200
* Default case: MID_TOWER
* Default cooling: STOCK_AIR

The Builder methods return `this`, which allows method chaining.

For example:

`builder.ramGb(16).storageGb(1000).ramFrequency(3200)`

After all required options are selected, the `build()` method creates the final object.

## 7. Validation

An important part of the project is configuration validation.

The `build()` method performs three groups of validation:

1. Single field validation
2. Component compatibility validation
3. Preset validation

### 7.1 Single Field Validation

The project checks that basic values are valid.

Examples:

* Computer name cannot be empty.
* CPU is required.
* RAM must be at least 4 GB.
* Storage must be at least 128 GB.
* Power supply must be at least 250 W.
* RAM slots must be between 1 and 4.
* RAM frequency must be at least 800.

If a value is invalid, the program throws an `IllegalArgumentException`.

## 8. Component Compatibility

The project also checks whether different components can work together.

### CPU and Motherboard

The CPU socket must match the motherboard socket.

If the sockets are different, the configuration is rejected.

### RAM and Motherboard

The RAM generation must match the motherboard.

For example, incompatible RAM generations cannot be used together.

The project also checks:

* Maximum number of RAM slots
* Maximum supported RAM frequency

### GPU and Power Supply

The selected power supply must provide enough power for the GPU.

If the GPU requires more power than the power supply can provide, the configuration is rejected.

### Dedicated GPU

The project checks the relationship between the GPU type and the `dedicatedGpu` flag.

An integrated GPU cannot be marked as dedicated.

A separate GPU must be marked as dedicated.

### CPU and GPU Balance

The project contains a basic CPU/GPU bottleneck rule.

If a weak CPU is combined with a high-performance dedicated GPU, the configuration is rejected.

This prevents unrealistic configurations such as a very weak CPU with a high-end GPU.

## 9. Cooling Validation

The project also checks whether the cooling system is suitable for the CPU.

The CPU TDP is compared with the maximum TDP supported by the selected cooling type.

The project also checks whether the cooler can physically fit inside the selected computer case.

An additional rule is used for the Core i9 CPU.

In this project model, an i9 requires liquid cooling.

A Mini Tower case cannot be used with the selected AIO liquid cooling system.

## 10. Presets

The project contains different configuration presets.

The main presets are:

* `CUSTOM`
* `STUDY`
* `GAMING`

### CUSTOM

The `CUSTOM` preset allows the user to create a configuration using the normal validation rules.

### STUDY

The `STUDY` preset requires at least 12 GB of RAM.

This configuration is intended for studying and running several programs at the same time.

### GAMING

The `GAMING` preset has additional requirements.

A gaming computer must have:

* At least 16 GB RAM
* A dedicated GPU
* An additional cooling system
* A CPU with a sufficient performance level
* A GPU with a sufficient performance level

These rules prevent a configuration from being marked as gaming when it does not have the required hardware.

## 11. Error Handling

The project uses `IllegalArgumentException` for invalid configurations.

This means that an incorrect configuration cannot be created.

For example, if the RAM is too small, the program throws an exception with a message explaining the problem.

Examples of validation messages include:

* `RAM must be at least 4 GB`
* `Storage must be at least 128 GB`
* `Power supply is too weak for selected GPU`
* `CPU socket is incompatible with motherboard`
* `RAM generation is incompatible with motherboard`
* `Gaming requires at least 16 GB RAM`
* `Gaming requires a dedicated GPU`

These messages make it easier to understand why a configuration is invalid.

## 12. Advantages of the Solution

The Builder Pattern provides several advantages in this project.

First, it makes object creation easier to understand.

Second, it avoids a constructor with a large number of parameters.

Third, optional values can have default settings.

Fourth, validation is performed before the final object is created.

Finally, the project can be extended with new computer components and validation rules without changing the basic object creation process.

## 13. Testing

The project should be tested with both valid and invalid configurations.

Examples of valid tests:

* A normal custom computer configuration
* A study computer with at least 12 GB RAM
* A gaming computer with at least 16 GB RAM and a dedicated GPU
* A configuration with compatible CPU and motherboard
* A configuration with compatible RAM and motherboard

Examples of invalid tests:

* Less than 4 GB RAM
* Less than 128 GB storage
* Weak power supply
* Incompatible CPU socket and motherboard
* Incompatible RAM generation
* Too many RAM slots
* RAM frequency higher than motherboard support
* Gaming configuration without a dedicated GPU
* Gaming configuration with less than 16 GB RAM
* Weak cooling for a high-TDP CPU
* i9 without liquid cooling
* Mini Tower with AIO liquid cooling
* Weak CPU combined with a high-performance GPU

## 14. Conclusion

In this project, I implemented the Builder Design Pattern for creating computer configurations in Java.

The Builder allows the computer to be configured step by step and provides default values for optional components.

I also implemented validation rules for individual values, hardware compatibility, cooling, CPU/GPU balance and configuration presets.

The project helped me understand how the Builder Pattern works and how design patterns can be used to make Java code more structured, readable and easier to maintain.
