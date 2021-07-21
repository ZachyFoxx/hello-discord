plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://m2.dv8tion.net/releases")}
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.google.guava:guava:30.1-jre")

    // Discord library
    implementation("net.dv8tion:JDA:4.3.0_277")

    // dotenv
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")

}

tasks {
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "sh.foxboy.HelloDiscordKt"
        }
    }
    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        project.setProperty("mainClassName", "sh.foxboy.HelloDiscordKt")
        archiveClassifier.set("")
    }
}