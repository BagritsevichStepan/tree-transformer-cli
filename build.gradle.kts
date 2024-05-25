plugins {
    id("java")
}

group = "org.tree"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.tree.TreeCLI"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}