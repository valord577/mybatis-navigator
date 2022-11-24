fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    // https://plugins.gradle.org/plugin/org.jetbrains.intellij
    // https://github.com/JetBrains/gradle-intellij-plugin/releases
    id("org.jetbrains.intellij") version "1.10.0"
}

val jdkVersion: String = System.getProperty("java.version")
val jdkCurrent = JavaVersion.toVersion(jdkVersion)

// project compatibility
val projVersion = JavaVersion.VERSION_17
if (!jdkCurrent.isCompatibleWith(projVersion)) {
    throw GradleException(
        "This project is not compatible with the current JDK. \n" +
                "  Require JDK: `${projVersion}` or higher"
    )
}

configure<JavaPluginExtension> {
    sourceCompatibility = projVersion
    targetCompatibility = projVersion
}

tasks.withType<JavaCompile> {
    options.encoding = "utf-8"
    options.compilerArgs.add("-Xlint:unchecked")
    options.compilerArgs.add("-Xlint:deprecation")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies { }

val intellijPlatform = prop("intellijPlatform")

val pluginGroup = prop("pluginGroup")
val pluginSchema = prop("pluginSchema")
val pluginVersion = prop("pluginVersion")

val artifactVersion = "${pluginVersion}-build${intellijPlatform}"

group = pluginGroup
version = artifactVersion

intellij {
    // sandboxDir.set(".sandbox/${intellijPlatform}-EAP-SNAPSHOT")

    pluginName.set(pluginSchema)
    version.set("${intellijPlatform}-EAP-SNAPSHOT")
    plugins.set(
        listOf("com.intellij.java")
    )
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set(artifactVersion)

        untilBuild.set("${intellijPlatform}.*")

        pluginDescription.set(file("doc/description.html").readText())
        changeNotes.set(file("doc/changeNotes.html").readText())
    }
}
