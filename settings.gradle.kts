pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
		maven { url = uri("https://maven.architectury.dev/") }
		maven { url = uri("https://maven.fabricmc.net/") }
		maven { url = uri("https://maven.minecraftforge.net/") }
		maven { url = uri("https://maven.blamejared.com/") }
	}
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
	id("com.gradle.develocity") version "3.19"
}

develocity {
	buildScan.termsOfUseUrl = "https://gradle.com/terms-of-service"
	buildScan.termsOfUseAgree = "yes"
	buildScan.publishing.onlyIf {
		System.getenv("GITHUB_ACTIONS") == "true" &&
				it.buildResult.failures.isNotEmpty()
	}
}

rootProject.name = "archie-plugin"

includeBuild("plugin-build")
