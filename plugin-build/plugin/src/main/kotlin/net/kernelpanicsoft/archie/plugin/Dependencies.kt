package net.kernelpanicsoft.archie.plugin

import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.util.ModPlatform
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.attributes.Attribute
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderConvertible
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

val patchedFMLModType = Attribute.of("patchedFMLModType", Boolean::class.javaObjectType)

fun <T : ModuleDependency> DependencyHandlerScope.bundleRuntimeLibrary(
	dependency: T,
	dependencyConfiguration: T.() -> Unit = {}
)
{
	"include"(dependency) {
		isTransitive = false
		dependencyConfiguration()
	}
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	when (extensions.getByType<LoomGradleExtensionAPI>().platform.get())
	{
		ModPlatform.NEOFORGE ->
			"localRuntime"(dependency) {
				isTransitive = false
				dependencyConfiguration()
				attributes {
					attribute(patchedFMLModType, true)
				}
			}

		else -> Unit
	}

}

fun <T : ModuleDependency> DependencyHandlerScope.bundleMod(
	dependency: T,
	dependencyConfiguration: T.() -> Unit = {}
)
{
	"include"(dependency) {
		dependencyConfiguration()
	}
	"modApi"(dependency) {
		dependencyConfiguration()
	}
}

fun DependencyHandlerScope.bundleRuntimeLibrary(
	group: String,
	name: String,
	version: String? = null,
	configuration: String? = null,
	classifier: String? = null,
	ext: String? = null,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(group, name, version, configuration, classifier, ext) {
		isTransitive = false
		dependencyConfiguration()
	}
	"implementation"(group, name, version, configuration, classifier, ext) {
		dependencyConfiguration()
	}
	when (extensions.getByType<LoomGradleExtensionAPI>().platform.get())
	{
		ModPlatform.NEOFORGE ->
			"localRuntime"(group, name, version, configuration, classifier, ext) {
				isTransitive = false
				dependencyConfiguration()
				attributes {
					attribute(patchedFMLModType, true)
				}
			}

		else -> Unit
	}

}

fun DependencyHandlerScope.bundleMod(
	group: String,
	name: String,
	version: String? = null,
	configuration: String? = null,
	classifier: String? = null,
	ext: String? = null,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(group, name, version, configuration, classifier, ext) {
		dependencyConfiguration()
	}
	"modApi"(group, name, version, configuration, classifier, ext) {
		dependencyConfiguration()
	}
}

fun DependencyHandlerScope.bundleRuntimeLibrary(
	dependencyNotation: String,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(dependencyNotation) {
		isTransitive = false
		dependencyConfiguration()
	}
	"implementation"(dependencyNotation) {
		dependencyConfiguration()
	}
	when (extensions.getByType<LoomGradleExtensionAPI>().platform.get())
	{
		ModPlatform.NEOFORGE ->
			"localRuntime"(dependencyNotation) {
				isTransitive = false
				dependencyConfiguration()
				attributes {
					attribute(patchedFMLModType, true)
				}
			}

		else -> Unit
	}
}

fun DependencyHandlerScope.bundleMod(
	dependencyNotation: String,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(dependencyNotation) {
		dependencyConfiguration()
	}
	"modApi"(dependencyNotation) {
		dependencyConfiguration()
	}
}

@Suppress("UnstableApiUsage")
fun <T : Any> DependencyHandlerScope.bundleRuntimeLibrary(
	dependency: Provider<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(dependency) {
		isTransitive = false
		dependencyConfiguration()
	}
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	when (extensions.getByType<LoomGradleExtensionAPI>().platform.get())
	{
		ModPlatform.NEOFORGE ->
			"localRuntime"(dependency) {
				isTransitive = false
				dependencyConfiguration()
				attributes {
					attribute(patchedFMLModType, true)
				}
			}

		else -> Unit
	}
}

@Suppress("UnstableApiUsage")
fun <T : Any> DependencyHandlerScope.bundleMod(
	dependency: Provider<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(dependency) {
		dependencyConfiguration()
	}
	"modApi"(dependency) {
		dependencyConfiguration()
	}
}

@Suppress("UnstableApiUsage")
fun <T : Any> DependencyHandlerScope.bundleRuntimeLibrary(
	dependency: ProviderConvertible<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(dependency) {
		isTransitive = false
		dependencyConfiguration()
	}
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	when (extensions.getByType<LoomGradleExtensionAPI>().platform.get())
	{
		ModPlatform.NEOFORGE ->
			"localRuntime"(dependency) {
				isTransitive = false
				dependencyConfiguration()
				attributes {
					attribute(patchedFMLModType, true)
				}
			}

		else -> Unit
	}
}

@Suppress("UnstableApiUsage")
fun <T : Any> DependencyHandlerScope.bundleMod(
	dependency: ProviderConvertible<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"include"(dependency) {
		dependencyConfiguration()
	}
	"modApi"(dependency) {
		dependencyConfiguration()
	}
}
