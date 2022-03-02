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
 *       Fraunhofer Institute for Software and Systems Engineering - added dependencies
 *
 */

plugins {
    `java-library`
    id("application")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

val jupiterVersion: String by project
val rsApi: String by project
val edcCoreVersion: String by project

dependencies {
     implementation("org.eclipse.dataspaceconnector:core:${edcCoreVersion}")

     implementation("org.eclipse.dataspaceconnector:assetindex-memory:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:transfer-store-memory:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:contractdefinition-store-memory:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:configuration-fs:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:assetindex-memory:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:negotiation-store-memory:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:http:${edcCoreVersion}")

     implementation("org.eclipse.dataspaceconnector:s3-provision:${edcCoreVersion}")

     implementation("org.eclipse.dataspaceconnector:iam-mock:${edcCoreVersion}")
     implementation(project(":extensions:azure:vault"))

     implementation("org.eclipse.dataspaceconnector:control-api:${edcCoreVersion}")

     implementation("org.eclipse.dataspaceconnector:ids:${edcCoreVersion}")

     implementation(project(":samples:05-file-transfer-cloud:api"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:${jupiterVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jupiterVersion}")
}

application {
    mainClass.set("org.eclipse.dataspaceconnector.boot.system.runtime.BaseRuntime")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    exclude("**/pom.properties", "**/pom.xm")
    mergeServiceFiles()
    archiveFileName.set("consumer.jar")
}
