plugins {
    id("java")
    id("maven-publish")
}

group = "dev.arctic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // PaperMC repository
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT") // PaperAPI 1.21
    compileOnly("org.projectlombok:lombok:1.18.30") // Lombok for annotations
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21)) // Set Java version to 21
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// JitPack configuration
tasks.register("sourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register("javadocJar", Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

artifacts {
    archives(tasks["sourcesJar"])
    archives(tasks["javadocJar"])
}