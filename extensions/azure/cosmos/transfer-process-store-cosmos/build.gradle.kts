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

val cosmosSdkVersion: String by project
val jodahFailsafeVersion: String by project

dependencies {
    api("org.eclipse.dataspaceconnector:transfer-spi:${edcCoreVersion}")
    api("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}")
    api(project(":extensions:azure:cosmos:cosmos-common"))

    implementation("com.azure:azure-cosmos:${cosmosSdkVersion}")
    implementation("net.jodah:failsafe:${jodahFailsafeVersion}")

    testImplementation("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}:test-fixtures")
    testImplementation(testFixtures(project(":extensions:azure:azure-test")))
}


publishing {
    publications {
        create<MavenPublication>("transfer-process-store-cosmos") {
            artifactId = "transfer-process-store-cosmos"
            from(components["java"])
        }
    }
}
