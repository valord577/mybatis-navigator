fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    // https://plugins.gradle.org/plugin/org.jetbrains.intellij
    // https://github.com/JetBrains/gradle-intellij-plugin/releases
    id("org.jetbrains.intellij") version "1.9.0"
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

val intellijVersion = prop("intellijVersion")
val intellijLatest = prop("intellijLatest")

val pluginGroup = prop("pluginGroup")
val pluginSchema = prop("pluginSchema")
val pluginVersion = prop("pluginVersion")

val artifactVersion =
    "${pluginVersion}-build${intellijLatest.replace('*', '^')}"

group = pluginGroup
version = artifactVersion

intellij {
    //sandboxDir.set(".sandbox/${intellijVersion}")

    pluginName.set(pluginSchema)
    version.set(intellijVersion)
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

        untilBuild.set(intellijLatest)

        pluginDescription.set(file("doc/description.html").readText())
        changeNotes.set(file("doc/changeNotes.html").readText())
    }
}
