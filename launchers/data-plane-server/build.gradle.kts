/*
 *  Copyright (c) 2022 Microsoft Corporation
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

val okHttpVersion: String by project
val jodahFailsafeVersion: String by project
val edcCoreVersion: String by project

plugins {
    `java-library`
    id("application")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

dependencies {
     implementation("org.eclipse.dataspaceconnector:web-spi:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:core-base:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:core-boot:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:http:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:data-plane-spi:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:data-plane-framework:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:data-plane-http:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:data-plane-api:${edcCoreVersion}")
     implementation(project(":extensions:azure:data-plane-azure-storage"))
}

application {
    mainClass.set("org.eclipse.dataspaceconnector.boot.system.runtime.BaseRuntime")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    exclude("**/pom.properties", "**/pom.xm", "jndi.properties", "jetty-dir.css", "META-INF/maven/**")
    mergeServiceFiles()
    archiveFileName.set("data-plane-server.jar")
}


publishing {
    publications {
        create<MavenPublication>("data-plane-server") {
            artifactId = "data-plane-server"
            from(components["java"])
        }
    }
}


