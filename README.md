# Java Core Kick: Arrays (Task 1)

Console application that reads integer arrays from a text file and works with them.

## What it does

- Reads lines from `data/arrays.txt`; numbers may be separated by commas, semicolons or spaces.
  Invalid lines are skipped with a warning.
- Builds a `CustomArray` for every valid line and stores it in a repository.
- Calculates min, max, sum and average, and sorts arrays (bubble and insertion sort).
- Keeps the statistics of every array in `ArrayWarehouse`; an observer recalculates them
  whenever an array changes.
- Searches the repository with specifications and sorts it with comparators.

## Requirements

- JDK 21
- Maven 3.9+

## Build and test

```bash
mvn verify
```

`verify` also runs the Spotless formatting check. To fix formatting:

```bash
mvn spotless:apply
```

## Run

Run `com.haponenka.app.ArrayApp` from the project root (the data file path is relative to it).
Logs go to the console and to `logs/app.log`.
