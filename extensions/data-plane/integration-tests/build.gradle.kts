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

plugins {
    java
    `java-test-fixtures`
}

val edcCoreVersion: String by project

val jupiterVersion: String by project
val restAssured: String by project
val assertj: String by project
val awaitility: String by project
val faker: String by project
val httpMockServer: String by project

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${jupiterVersion}")
    testImplementation("io.rest-assured:rest-assured:${restAssured}")
    testImplementation("org.assertj:assertj-core:${assertj}")
    testImplementation("org.awaitility:awaitility:${awaitility}")
    testImplementation("com.github.javafaker:javafaker:${faker}")
    testImplementation("org.mock-server:mockserver-netty:${httpMockServer}:shaded")
    testImplementation("org.mock-server:mockserver-client-java:${httpMockServer}:shaded")

    testImplementation("org.eclipse.dataspaceconnector:common-util:${edcCoreVersion}:test-fixtures")
    testImplementation("org.eclipse.dataspaceconnector:junit-extension:${edcCoreVersion}:test-fixtures")
    testImplementation("org.eclipse.dataspaceconnector:data-plane-http:${edcCoreVersion}:test-fixtures")
    testImplementation("org.eclipse.dataspaceconnector:data-plane-spi:${edcCoreVersion}")

    testFixturesImplementation("org.junit.jupiter:junit-jupiter-api:${jupiterVersion}")
    testFixturesRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jupiterVersion}")

    testRuntimeOnly(project(":launchers:data-plane-server"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
