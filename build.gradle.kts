import org.gradle.api.JavaVersion
import org.gradle.api.GradleException


val compileJvmTarget = JavaVersion.toVersion(extra["CompileJvmTarget"].toString())
val currentJdkVersion = JavaVersion.current()
if (!(currentJdkVersion.isCompatibleWith(compileJvmTarget))) {
    throw GradleException(
        "This project is not compatible with the current JDK. \n" +
        "  Require JDK: $compileJvmTarget or higher."
    )
}

tasks.register("clean") {
    delete(rootProject.layout.buildDirectory)
}

ext.properties.entries.forEach {
    println("extra['${it.key}']: ${it.value}")
}
