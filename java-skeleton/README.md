# Java Project Skeleton

Standard Maven-style Java project structure.

## Structure
- `src/main/java` – application source
- `src/main/resources` – application resources
- `src/test/java` – test source (JUnit 5)
- `pom.xml` – Maven configuration

## Requirements
- JDK 17+
- Maven (optional; scripts will fall back to `javac` if Maven not found)

## Build
```bash
./scripts/build.sh
```

## Test
```bash
./scripts/test.sh
```

## Run
```bash
./scripts/run.sh "Your Name"
```