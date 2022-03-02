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

val eventGridSdkVersion: String by project

dependencies {
    api("org.eclipse.dataspaceconnector:spi:${edcCoreVersion}")
    api("com.azure:azure-messaging-eventgrid:${eventGridSdkVersion}")

}


publishing {
    publications {
        create<MavenPublication>("azure-eventgrid-config") {
            artifactId = "azure-eventgrid-config"
            from(components["java"])
        }
    }
}
