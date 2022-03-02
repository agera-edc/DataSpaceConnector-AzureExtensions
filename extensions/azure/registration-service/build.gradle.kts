plugins {
    `java-library`
}
val edcCoreVersion: String by project

val jwtVersion: String by project
val rsApi: String by project
val okHttpVersion: String by project

dependencies {
    implementation(project(":extensions:azure:events-config"))
    implementation("org.eclipse.dataspaceconnector:identity-did-spi:${edcCoreVersion}")

    // third party
    implementation("com.squareup.okhttp3:okhttp:${okHttpVersion}")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:${rsApi}")
    implementation("org.quartz-scheduler:quartz:2.3.0")
}

publishing {
    publications {
        create<MavenPublication>("registration-service") {
            artifactId = "registration-service"
            from(components["java"])
        }
    }
}
