import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.api.internal.artifacts.dependencies.DefaultMinimalDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultMutableVersionConstraint
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
	`kotlin-dsl`
	`kotlin-dsl-precompiled-script-plugins`
	alias(libs.plugins.pluginPublish)
}

val localProperties = kotlin.runCatching { loadProperties("$rootDir/local.properties") }.getOrNull()

val String.prop: String?
	get() = rootProject.properties[this] as String?

val String.local: String?
	get() = localProperties?.get(this) as String?

val String.env: String?
	get() = System.getenv(this)

val String.localOrEnv: String?
	get() = localProperties?.get(this)?.toString() ?: System.getenv(this.uppercase())

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
    compilerOptions {
	    jvmTarget.set(JvmTarget.JVM_17)
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
			name = "kernelpanic"
			val releasesRepoUrl = "https://repo.kernelpanicsoft.net/maven/releases"
			val snapshotsRepoUrl = "https://repo.kernelpanicsoft.net/maven/snapshots"
			url = uri(
				if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString()
						.startsWith("0")
				) snapshotsRepoUrl else releasesRepoUrl
			)
			credentials(PasswordCredentials::class) {
				username = "gradle_maven_publish_key".localOrEnv
				password = "gradle_maven_publish_secret".localOrEnv
			}
			authentication {
				create<BasicAuthentication>("basic")
			}
		}
	}
}
