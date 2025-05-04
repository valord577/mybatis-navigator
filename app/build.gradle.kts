fun prop(key: String) = extra[key].toString()

val (intellijMajor, intellijMinor) = prop("intellijBuilds").split(".")
val artifactVersion = "${prop("pluginVersion")}-build${intellijMajor}"

plugins {
    id("org.jetbrains.intellij.platform")
}
repositories {
    mavenLocal()
    mavenCentral()

    intellijPlatform {
        snapshots()
        localPlatformArtifacts()
        intellijDependencies()
        jetbrainsRuntime()
    }
}
dependencies {
    intellijPlatform {
        intellijIdeaCommunity(
            "${prop("intellijBuilds")}-EAP-CANDIDATE-SNAPSHOT", useInstaller = false
        )
        bundledPlugin("com.intellij.java")
        jetbrainsRuntime()
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = false

    pluginConfiguration {
        id = "${prop("pluginGroup")}.${prop("pluginSchema")}"
        name = prop("pluginSchema")
        version = artifactVersion
        description = file("doc/description.html").readText()
        changeNotes = file("doc/changeNotes.html").readText()

        ideaVersion {
            untilBuild = provider { null }
        }
        vendor {
            name = "valord577"
            email = "valord577@gmail.com"
            url = "https://github.com/valord577/mybatis-navigator"
        }
    }
}

tasks {
    compileJava {
        sourceCompatibility = prop("compileJvmTarget")
        targetCompatibility = prop("compileJvmTarget")
        options.encoding = "utf-8"
        options.compilerArgs.add("-Xlint:unchecked")
        options.compilerArgs.add("-Xlint:deprecation")
    }
    jar {
        archiveBaseName = prop("pluginSchema")
        archiveVersion = artifactVersion
    }
    composedJar {
        archiveBaseName = prop("pluginSchema")
        archiveVersion = artifactVersion
    }
    buildPlugin {
        archiveBaseName = prop("pluginSchema")
        archiveVersion = artifactVersion
    }

    publishPlugin {
        token = (System.getenv(prop("pluginPublishTokenKey")))
    }
}
