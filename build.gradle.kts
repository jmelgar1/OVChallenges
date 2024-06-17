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
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT") {
        exclude("junit", "junit")
    }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    compileOnly(group = "net.luckperms", name = "api", version = "5.4")
    compileOnly(group = "net.coreprotect", name = "coreprotect", version = "22.0")
}

//tasks {
////    compileJava {
////        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
////        // See https://openjdk.java.net/jeps/247 for more information.
////        options.release.set(21)
////    }
//    javadoc {
//        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
//    }
//}