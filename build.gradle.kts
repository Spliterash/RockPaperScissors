plugins {
    `java-library`
    id("io.freefair.lombok") version "8.1.0"

}

version = "1.0.0"

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.freefair.lombok")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    repositories {
        mavenCentral()
        maven("https://repo.clojars.org/")
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:3.1.1"))
        implementation("org.jetbrains:annotations:24.0.1")
    }

}