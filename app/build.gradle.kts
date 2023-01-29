fun prop(key: String) = extra[key].toString()

plugins {
    id("java")
    id("org.jetbrains.intellij")
}

val compileJvmTarget = JavaVersion.toVersion(prop("compileJvmTarget"))

configure<JavaPluginExtension> {
    sourceCompatibility = compileJvmTarget
    targetCompatibility = compileJvmTarget
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
    sandboxDir.set(".sandbox/${intellijPlatform}-EAP-SNAPSHOT")

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
    publishPlugin {
        channels.set(
            listOf("Stable")
        )
        token.set(System.getenv(prop("pluginPublishTokenKey")))
    }

    patchPluginXml {
        version.set(artifactVersion)

        untilBuild.set("${intellijPlatform}.*")

        pluginDescription.set(file("doc/description.html").readText())
        changeNotes.set(file("doc/changeNotes.html").readText())
    }
}
