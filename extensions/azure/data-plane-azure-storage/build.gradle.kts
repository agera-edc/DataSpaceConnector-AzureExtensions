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
val storageBlobVersion: String by project;
val jodahFailsafeVersion: String by project

plugins {
    `java-library`
}
val edcCoreVersion: String by project

dependencies {
    api("org.eclipse.dataspaceconnector:data-plane-spi:${edcCoreVersion}")
    implementation("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}")
    implementation("com.azure:azure-storage-blob:${storageBlobVersion}")
    implementation("net.jodah:failsafe:${jodahFailsafeVersion}")

    testImplementation(testFixtures(project(":extensions:azure:azure-test")))
    testImplementation("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}:test-fixtures")
}

publishing {
    publications {
        create<MavenPublication>("data-plane-azure-storage") {
            artifactId = "data-plane-azure-storage"
            from(components["java"])
        }
    }
}
