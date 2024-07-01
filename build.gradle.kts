plugins {
    `java-library`
}

//java {
//    // Configure the java toolchain. This allows gradle to auto-provision JDK 21 on systems that only have JDK 11 installed for example.
//    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
//}

repositories {
    mavenCentral()
    maven {
        name = "paper"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven(url = "https://jitpack.io")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://maven.playpro.com")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT") {
        exclude("junit", "junit")
    }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude(group = "org.bukkit", module = "bukkit")
    }
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}