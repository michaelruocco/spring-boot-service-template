# Spring Boot Service Template

[![Build](https://github.com/michaelruocco/spring-boot-service-template/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/spring-boot-service-template/actions)
[![codecov](https://codecov.io/gh/michaelruocco/spring-boot-service-template/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/spring-boot-service-template)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/spring-boot-service-template/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/spring-boot-service-template&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/spring-boot-service-template?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-boot-service-template&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-boot-service-template)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-boot-service-template&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-boot-service-template)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-boot-service-template&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-boot-service-template)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-boot-service-template&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-boot-service-template)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/spring-boot-service-template.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22spring-boot-service-template%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## TODO

1.  Add postgres db implementation / with flyway and run with docker compose
2.  Add OIDC/keycloak configuration integration
3.  Add kafka integration
4.  Add postman examples

## Overview

This is a template project for creating spring boot projects more efficiently and consistently.
It does not include test fixtures.

*   [Lombok](https://projectlombok.org/) for boilerplate code generation

*   [AssertJ](https://joel-costigliola.github.io/assertj/) for fluent and readable assertions

*   [SLF4J](http://www.slf4j.org/) for abstracted and pluggable logging

*   [JUnit5](https://junit.org/junit5/) for unit testing

*   [Mockito](https://site.mockito.org/) for mocking

*   [Axion release plugin](https://github.com/allegro/axion-release-plugin) for version management

*   [Spotless plugin](https://github.com/diffplug/spotless/tree/main/plugin-gradle) for code formatting

*   [Nebula plugin](https://github.com/nebula-plugins/gradle-lint-plugin) for gradle linting

*   [Versions plugin](https://github.com/ben-manes/gradle-versions-plugin) for monitoring dependency versions

*   [Jacoco plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html) for code coverage reporting

*   [Test Logger plugin](https://plugins.gradle.org/plugin/com.adarshr.test-logger) for pretty printing of test
    results when running tests from gradle
    
*   [Github actions](https://github.com/actions) for the build pipeline

*   [Maven publish plugin](https://docs.gradle.org/current/userguide/publishing_maven.html) for publishing snapshots
    and releases to [Maven Central](https://search.maven.org/)
    
*   [Nexus staging plugin](https://github.com/Codearte/gradle-nexus-staging-plugin) to automatically close and drop
    releases published to [Maven Central](https://search.maven.org/)

*   [Better code hub](https://bettercodehub.com/) for code and architecture analysis

*   [Codecov](https://codecov.io/) for code coverage analysis

*   [Sonar Cloud](https://sonarcloud.io/) for static code analysis 

*   [Codacy](https://www.codacy.com/) for additional static code and coverage analysis

*   [Spring Boot](https://spring.io/projects/spring-boot) for web service functionality

*   [Open API Generator](https://github.com/OpenAPITools/openapi-generator) for generating
    API endpoint code and API client code from an Open API specification.

For a number of the above tools to work your Github Actions pipeline will require the
following secrets to be set up:

*   SONAR_TOKEN for [Sonar Cloud](https://sonarcloud.io/) analysis
*   CODACY_TOKEN for [Codacy](https://www.codacy.com/) analysis
*   OSSRH_USERNAME and OSSRH_PASSWORD for releasing snapshots and releases to Maven Central
*   OSSRH_PGP_SECRET_KEY and OSSRH_PGP_SECRET_KEY_PASSWORD for signing release artifacts before pushing to maven central

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// checks dependency versions
// checks for gradle issues
// formats code
// builds code
// runs tests
// runs integration tests
// builds docker image
// runs docker image
./gradlew clean currentVersion dependencyUpdates criticalLintGradle spotlessApply build integrationTest buildImage composeUp
```

```bash
cd app/spring-app
docker-compose up -d
cd ../..
./gradlew bootRun
```