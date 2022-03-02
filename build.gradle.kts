/*
 *  Copyright (c) 2020, 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

plugins {
    `java-library`
    `maven-publish`
    checkstyle
    jacoco
    id("com.rameshkp.openapi-merger-gradle-plugin") version "1.0.4"
}

repositories {
    mavenCentral()
}


val jetBrainsAnnotationsVersion: String by project
val jacksonVersion: String by project
val javaVersion: String by project
val jupiterVersion: String by project
val mockitoVersion: String by project
val rsApi: String by project
val swaggerJaxrs2Version: String by project

val groupId: String = "org.eclipse.dataspaceconnector"
var edcVersion: String = "0.0.1-SNAPSHOT"

if (project.version == "unspecified") {
    logger.warn("No version was specified, setting default 0.0.1-SNAPSHOT")
    logger.warn("If you want to change this, supply the -Pversion=X.Y.Z parameter")
    logger.warn("")
} else {
    edcVersion = project.version as String
}

subprojects {

    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.iais.fraunhofer.de/artifactory/eis-ids-public/")
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/agera-edc/DataSpaceConnector-Core")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    tasks.register<DependencyReportTask>("allDependencies") {}
}

buildscript {
    dependencies {
        classpath("io.swagger.core.v3:swagger-gradle-plugin:2.1.12")
    }
}

allprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "checkstyle")
    apply(plugin = "java")

    if (System.getenv("JACOCO") == "true") {
        apply(plugin = "jacoco")
    }

    checkstyle {
        toolVersion = "9.0"
        configFile = rootProject.file("resources/edc-checkstyle-config.xml")
        maxErrors = 0 // does not tolerate errors ...
        maxWarnings = 0 // ... or warnings
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(javaVersion))
        }
    }

    // EdcRuntimeExtension uses this to determine the runtime classpath of the module to run.
    tasks.register("printClasspath") {
        doLast {
            println(sourceSets["main"].runtimeClasspath.asPath);
        }
    }

    pluginManager.withPlugin("java-library") {
        group = groupId
        version = edcVersion

        dependencies {
            api("org.jetbrains:annotations:${jetBrainsAnnotationsVersion}")
            api("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
            api("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
            api("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
            api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}")

            testImplementation("org.junit.jupiter:junit-jupiter-api:${jupiterVersion}")
            testImplementation("org.junit.jupiter:junit-jupiter-params:${jupiterVersion}")
            testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jupiterVersion}")
            testImplementation("org.mockito:mockito-core:${mockitoVersion}")
            testImplementation("org.assertj:assertj-core:3.19.0")
            testImplementation("com.github.javafaker:javafaker:1.0.2")
        }

        publishing {
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/agera-edc/DataSpaceConnector-Extensions")
                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }

    }

    pluginManager.withPlugin("io.swagger.core.v3.swagger-gradle-plugin") {

        dependencies {
            // this is used to scan the classpath and generate an openapi yaml file
            implementation("io.swagger.core.v3:swagger-jaxrs2-jakarta:${swaggerJaxrs2Version}")
            implementation("jakarta.ws.rs:jakarta.ws.rs-api:${rsApi}")
        }
// this is used to scan the classpath and generate an openapi yaml file
        tasks.withType<io.swagger.v3.plugins.gradle.tasks.ResolveTask> {
            outputFileName = project.name
            outputFormat = io.swagger.v3.plugins.gradle.tasks.ResolveTask.Format.YAML
            prettyPrint = true
            classpath = java.sourceSets["main"].runtimeClasspath
            buildClasspath = classpath
            resourcePackages = setOf("org.eclipse.dataspaceconnector")
            outputDir = file("${rootProject.projectDir.path}/resources/openapi/yaml")
        }
        configurations {
            all {
                exclude(group = "com.fasterxml.jackson.jaxrs", module = "jackson-jaxrs-json-provider")
            }
        }
    }



    tasks.withType<Test> {
        useJUnitPlatform()
    }
    tasks.withType<Test> {
        testLogging {
            events("failed")
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
    tasks.withType<Checkstyle> {
        reports {
            // lets not generate any reports because that is done from within the Github Actions workflow
            html.required.set(false)
            xml.required.set(false)
        }
    }

    tasks.jar {
        metaInf {
            from("${rootProject.projectDir.path}/LICENSE")
            from("${rootProject.projectDir.path}/NOTICE.md")
        }
    }

    // Generate XML reports for Codecov
    if (System.getenv("JACOCO") == "true") {
        tasks.jacocoTestReport {
            reports {
                xml.required.set(true)
            }
        }
    }
}

openApiMerger {
    val yamlDirectory = file("${rootProject.projectDir.path}/resources/openapi/yaml")

    inputDirectory.set(yamlDirectory)
    output {
        directory.set(file("${rootProject.projectDir.path}/resources/openapi/"))
        fileName.set("openapi")
        fileExtension.set("yaml")
    }
    openApi {
        openApiVersion.set("3.0.1")
        info {
            title.set("EDC REST API")
            description.set("All files merged by open api merger")
            version.set("1.0.0-SNAPSHOT")
            license {
                name.set("Apache License v2.0")
                url.set("http://apache.org/v2")
            }
        }
    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
