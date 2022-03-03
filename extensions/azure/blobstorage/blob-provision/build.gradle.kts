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
}
val edcCoreVersion: String by project

val storageBlobVersion: String by project
val jodahFailsafeVersion: String by project


dependencies {
    api("org.eclipse.dataspaceconnector:spi:${edcCoreVersion}")
    api(project(":extensions:azure:blobstorage:blob-core"))

    implementation("com.azure:azure-storage-blob:${storageBlobVersion}")

    api("net.jodah:failsafe:${jodahFailsafeVersion}")

     testImplementation(testFixtures(project(":extensions:azure:azure-test")))
     testImplementation("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}")
}

publishing {
    publications {
        create<MavenPublication>("blob-provision") {
            artifactId = "blob-provision"
            from(components["java"])
        }
    }
}
