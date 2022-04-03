# Stacks Cubic Monitor

[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![CodeQL](https://github.com/stacks-cubic/sc-monitor/workflows/CodeQL/badge.svg)](https://github.com/stacks-cubic/sc-monitor/security/code-scanning) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=stacks-cubic_sc-monitor&metric=bugs)](https://sonarcloud.io/summary/new_code?id=stacks-cubic_sc-monitor) [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=stacks-cubic_sc-monitor&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=stacks-cubic_sc-monitor)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=stacks-cubic_sc-monitor&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=stacks-cubic_sc-monitor) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=stacks-cubic_sc-monitor&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=stacks-cubic_sc-monitor) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=stacks-cubic_sc-monitor&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=stacks-cubic_sc-monitor) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=stacks-cubic_sc-monitor&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=stacks-cubic_sc-monitor)

Java Lightweight Hardware Monitoring Tool

* [Install](#install)
* [Use](#use)
  * [Full example](#full-example)
  * [Output to terminal](#output-to-terminal)
  * [Output to file](#output-to-file)
* [Exception](#exception)
  * [NoCollectorException](#nocollectorexception)
* [How to build](#how-to-build)
* [Acknowledgements](#acknowledgements)
* [License](#license)

## Install

> Not yet released

## Use

### Full example
```java
public class example {
    public void text() {
        try {
            // Create collection work
            // Param is collection interval time (seconds)
            Work work = new Work(60);
            // Set the location to save the log, it will not be saved if not set
            work.save("./")
                // No collection CPU data
                .excludeCPU()
                // No collection Disk data
                .excludeDisk()
                // No collection Memory data
                .excludeMemory()
                // No collection Network data
                .excludeNetwork()
                // Start work
                .run();
        } catch (NoCollectorException e) {
            // Minimum 1 collection item needs to be enabled!
        }
    }
}
```

### Output to terminal
```java
Work work = new Work();
work.run();
```

### Output to file
```java
Work work = new Work();
// Param is file save location
work.save("./").run();
```

## Exception

### NoCollectorException

The `Work` provides 4 exclusion methods, you will get this exception if you exclude all collection items

## How to build

`sc-monitor` uses Maven as its build tool.

Since we rely on `oshi-core` requires Java 8 or later.

## Acknowledgements

* [SonarCloud](https://sonarcloud.io/about)
* [GitHub Actions](https://github.com/features/actions)
* [oshi](https://github.com/oshi/oshi)
* [slf4j](https://github.com/qos-ch/slf4j)

## License
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).