//import org.anarres.gradle.plugin.jnaerator.JNAeratorTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
//    id("dev.atsushieno.jnaerator") version "1.0.100"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.java.dev.jna:jna:5.12.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

//tasks.jnaerator {
//    libraryName("input")
//    packageName("com.example.jna.input")
//    headerFiles("/usr/include/linux/input.h")
//}

//tasks.jnaerator {
//    libraryName("input")
//    packageName("com.example.jna.input")
//    headerFiles("/usr/include/linux/input.h")
//}
//tasks.create<JNAeratorTask>("jnaeratorPosix") {
//    setOutputDir(File(project.buildDir, "generated-sources/jnaerator-posix"))
//    libraryName("fcntl")
//    packageName("com.example.jna.fcntl")
//    headerFiles("/usr/include/fcntl.h")
//}
//val jnaeratorPosix = tasks.named<JNAeratorTask>("jnaeratorPosix").get()
//sourceSets.getByName("main").java.srcDir(jnaeratorPosix.outputDir);
//project.tasks.getByName("compileJava").dependsOn(jnaeratorPosix);