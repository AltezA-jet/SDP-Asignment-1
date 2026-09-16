# Assignment 1 — Builder Pattern: Computer Configuration

Individual variant for the **Software Design Patterns** course.

| | |
|---|---|
| **Domain** | Computer Configuration |
| **Constraint** | The `GAMING` preset raises the requirements of several unrelated fields at once: it demands at least 16 GB of RAM, a discrete graphics card, an enhanced cooling solution and a minimum performance level for **both** CPU and GPU. A configuration that every other preset would accept can therefore still be invalid as a gaming machine. |
| **Required preset** | `GAMING` (plus `BASIC` and `STUDY`) |
| **Language** | Java 17 |
| **Build** | Maven |

## Layout

```
assignment-1-builder/
├── docs/
│   ├── builder-uml.png          UML class diagram
│   └── builder-uml.puml         diagram source
├── src/
│   ├── main/java/com/example/computerconfiguration/
│   │   ├── ComputerConfiguration.java        Product + nested Builder
│   │   ├── ComputerConfigurationDirector.java Director (3 presets)
│   │   ├── Main.java                          Client
│   │   ├── Preset.java                        preset rules as data
│   │   ├── RamModule.java                     value object
│   │   ├── Cpu / Gpu / Motherboard / RamType / CaseType / CoolingType / Socket
│   │   └── legacy/
│   │       ├── LegacyComputerConfiguration.java  Part A: telescoping constructors
│   │       └── LegacyMain.java                   Part A: the problem at the call site
│   └── test/java/com/example/computerconfiguration/
│       └── ComputerConfigurationTest.java     17 tests in 5 groups
├── report.md
└── pom.xml
```

## Running

```bash
mvn -q compile exec:java -Dexec.mainClass=com.example.computerconfiguration.Main
mvn test
```

Without Maven:

```bash
javac -d out $(find src/main -name '*.java')
java -cp out com.example.computerconfiguration.Main
java -cp out com.example.computerconfiguration.legacy.LegacyMain   # Part A
```

## The pattern in one look

| Builder role | Class | Responsibility |
|---|---|---|
| Product | `ComputerConfiguration` | Immutable, fully validated configuration |
| Builder | `ComputerConfiguration.Builder` | Collects the parts, applies defaults, validates, produces the Product |
| Client | `Main` | Asks for presets, builds one custom machine, shows rejections |
| Director | `ComputerConfigurationDirector` | Defines the three catalogue machines once |
| Value object | `RamModule` | Memory specification that cannot be partially filled in |

A full write-up — the design problems, the validation rules, the Clean Code
before/after fragments and the rejected design alternatives — is in
[`report.md`](report.md).
