/*
 *  Copyright (c) 2021 Microsoft Corporation
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
    id("application")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
val edcCoreVersion: String by project


dependencies {
     implementation("org.eclipse.dataspaceconnector:core:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}")
     implementation(project(":extensions:azure:registration-service"))
     implementation("org.eclipse.dataspaceconnector:registration-service-api:${edcCoreVersion}")
     implementation("org.eclipse.dataspaceconnector:did-document-store-memory:${edcCoreVersion}")
}

application {
    mainClass.set("org.eclipse.dataspaceconnector.boot.system.runtime.BaseRuntime")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    exclude("**/pom.properties", "**/pom.xm")
    mergeServiceFiles()
    archiveFileName.set("reg-svc.jar")
}
