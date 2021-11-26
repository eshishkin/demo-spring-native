# Spring Native Demo Project

This is a simple project for testing capabilities of GraalVM Native Image with Spring Boot application.

## How to build

### Non Native Application

- Go to `reactive` or `non-reactive` directory
- Execute `mwnw clean package spring-boot:build-image`

### Native Application (Community Edition)

- Go to `reactive` or `non-reactive` directory
- Execute `mwnw clean package spring-boot:build-image -Pnative-prod-tiny`

### Liberica NIK (https://libericajdk.ru/pages/liberica-native-image-kit/)

For non-reactive application use the following command
```
docker build -f non-reactive/Dockerfile.liberica -t eshishkin/demo/spring-native/liberica-nik-blocking .
```

And this one for reactive one

```
docker build -f reactive/Dockerfile.liberica -t eshishkin/demo/spring-native/liberica-nik-netty .
```

### GraalVM EE

- Download zip file with GraalVM from Oracle website (https://www.oracle.com/downloads/graalvm-downloads.html?selected_tab=1)
- Copy it to `distr` directory with name `graalvm.tar.gz`
- Download `native-image.jar` from the Oracle website and put it to `distr` directory
- Execute command that builds an image


For non-reactive application use the following command
```
docker build -f non-reactive/Dockerfile.enterprise -t eshishkin/demo/spring-native/enterprise-serial --build-arg PROFILE=enterprise-serial-gc .
```

And this one for reactive one

```
docker build -f reactive/Dockerfile.enterprise -t eshishkin/demo/spring-native/enterprise-serial-netty --build-arg PROFILE=enterprise-serial-gc .
```

Where `PROFILE` is a maven profile. See supported profiles in `pom` files

## How to run

- Go to etc/docker-compose
- Run `docker-compose up -d`

## How to run a test

- Download Apache JMeter
- Open JMeter and load test plan (located in ./etc/jmeter)
- Run the test