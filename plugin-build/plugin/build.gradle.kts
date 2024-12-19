import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.api.internal.artifacts.dependencies.DefaultMinimalDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultMutableVersionConstraint
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
	`kotlin-dsl`
	`kotlin-dsl-precompiled-script-plugins`
	alias(libs.plugins.pluginPublish)
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	maven { url = uri("https://maven.architectury.dev/") }
	maven { url = uri("https://maven.fabricmc.net/") }
	maven { url = uri("https://maven.minecraftforge.net/") }
	maven { url = uri("https://maven.blamejared.com/") }
}

dependencies {
    implementation(kotlin("stdlib"))
	implementation(kotlin("script-runtime"))
	implementation(gradleApi())
	implementation(gradleKotlinDsl())

	implementation(libs.plugins.kotlin.toLibrary())
	implementation(libs.plugins.architectury.toLibrary())
	implementation(libs.plugins.architectury.loom.toLibrary())

	testImplementation(libs.junit)
}

fun ProviderConvertible<PluginDependency>.toLibrary() = asProvider().toLibrary()

fun Provider<PluginDependency>.toLibrary() = get().toLibrary()

fun PluginDependency.toLibrary() = DefaultMinimalDependency(
	DefaultModuleIdentifier.newId(pluginId, "$pluginId.gradle.plugin"),
	DefaultMutableVersionConstraint(version),
)

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

gradlePlugin {
    plugins {
		get(property("ID").toString()).apply {
            id = property("ID").toString()
            version = property("VERSION").toString()
            description = property("DESCRIPTION").toString()
            displayName = property("DISPLAY_NAME").toString()
        }
    }
}

gradlePlugin {
    website.set(property("WEBSITE").toString())
    vcsUrl.set(property("VCS_URL").toString())
}

publishing {
	repositories {
		maven {
			name = "milosworks"
			url = uri("https://maven.milosworks.xyz/releases")
			credentials(PasswordCredentials::class) {
				username = System.getenv("GRADLE_MAVEN_PUBLISH_KEY")
				password = System.getenv("GRADLE_MAVEN_PUBLISH_SECRET")
			}
			authentication {
				create<BasicAuthentication>("basic")
			}
		}
	}
}
