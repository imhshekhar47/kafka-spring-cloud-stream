plugins {
    `java-library`
}

dependencies {
    implementation("org.apache.kafka:kafka_2.12:2.3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.6")
}