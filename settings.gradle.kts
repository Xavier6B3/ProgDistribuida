pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id("io.quarkus") version "3.40.1.1"
    }
}

rootProject.name = "prog-distribuida"

include("web01")
include("web02")
include("web03")
include("app-authors")
include("app-books")

include("app-customers")