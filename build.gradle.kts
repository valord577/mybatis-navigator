buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        // see - https://github.com/JetBrains/gradle-intellij-plugin/releases
        classpath("org.jetbrains.intellij.plugins:gradle-intellij-plugin:1.3.0")
    }
}

apply(plugin = "java")
apply(plugin = "org.jetbrains.intellij")

val jdkVersion: String = System.getProperty("java.version")
val jdkCurrent = JavaVersion.toVersion(jdkVersion)

// project compatibility
val projectCompat = JavaVersion.VERSION_11
if (!jdkCurrent.isCompatibleWith(projectCompat)) {
    throw GradleException("This project is not compatible with the current JDK. \n  Require JDK: `${projectCompat}` or higher")
}

configure<JavaPluginExtension> {
    sourceCompatibility = projectCompat
    targetCompatibility = projectCompat
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

val intellijVersion = extra["intellijVersion"] as String
val intellijLatest = extra["intellijLatest"] as String

val pluginGroup = extra["pluginGroup"] as String
val pluginSchema = extra["pluginSchema"] as String
val pluginVersion = extra["pluginVersion"] as String

val artifactVersion = "${pluginVersion}-build${intellijLatest.replace('*', '^')}"

group = pluginGroup
version = artifactVersion

configure<org.jetbrains.intellij.IntelliJPluginExtension> {
//    sandboxDir.set(".sandbox/${intellijVersion}")

    version.set(intellijVersion)
    plugins.set(listOf("java"))
}

tasks.withType<org.jetbrains.intellij.tasks.PatchPluginXmlTask> {
    version.set(artifactVersion)

    untilBuild.set(intellijLatest)

    pluginDescription.set(file("doc/description.html").readText())
    changeNotes.set(file("doc/changeNotes.html").readText())
}
