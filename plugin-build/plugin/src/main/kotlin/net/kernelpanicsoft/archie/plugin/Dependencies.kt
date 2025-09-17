package net.kernelpanicsoft.archie.plugin

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderConvertible
import org.gradle.kotlin.dsl.DependencyHandlerScope


fun <T : ModuleDependency> DependencyHandlerScope.runtimeLibrary(
	dependency: T,
	dependencyConfiguration: T.() -> Unit = {}
)
{
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	"runtimeLibraryConfiguration"(dependency) {
		dependencyConfiguration()
	}
}

fun <T : ModuleDependency> DependencyHandlerScope.bundleRuntimeLibrary(
	dependency: T,
	dependencyConfiguration: T.() -> Unit = {}
)
{
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	"bundleRuntimeLibraryConfiguration"(dependency) {
		dependencyConfiguration()
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

fun DependencyHandlerScope.runtimeLibrary(
	group: String,
	name: String,
	version: String? = null,
	configuration: String? = null,
	classifier: String? = null,
	ext: String? = null,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(group, name, version, configuration, classifier, ext) {
		dependencyConfiguration()
	}
	"runtimeLibraryConfiguration"(group, name, version, configuration, classifier, ext) {
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
	"implementation"(group, name, version, configuration, classifier, ext) {
		dependencyConfiguration()
	}
	"bundleRuntimeLibraryConfiguration"(group, name, version, configuration, classifier, ext) {
		dependencyConfiguration()
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

fun DependencyHandlerScope.runtimeLibrary(
	dependencyNotation: String,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(dependencyNotation) {
		dependencyConfiguration()
	}
	"runtimeLibraryConfiguration"(dependencyNotation) {
		dependencyConfiguration()
	}
}

fun DependencyHandlerScope.bundleRuntimeLibrary(
	dependencyNotation: String,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(dependencyNotation) {
		dependencyConfiguration()
	}
	"bundleRuntimeLibraryConfiguration"(dependencyNotation) {
		dependencyConfiguration()
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

fun <T : Any> DependencyHandlerScope.runtimeLibrary(
	dependency: Provider<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	"runtimeLibraryConfiguration"(dependency) {
		dependencyConfiguration()
	}
}

fun <T : Any> DependencyHandlerScope.bundleRuntimeLibrary(
	dependency: Provider<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	"bundleRuntimeLibraryConfiguration"(dependency) {
		dependencyConfiguration()
	}
}

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

fun <T : Any> DependencyHandlerScope.runtimeLibrary(
	dependency: ProviderConvertible<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	"runtimeLibraryConfiguration"(dependency) {
		dependencyConfiguration()
	}
}

fun <T : Any> DependencyHandlerScope.bundleRuntimeLibrary(
	dependency: ProviderConvertible<T>,
	dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}
)
{
	"implementation"(dependency) {
		dependencyConfiguration()
	}
	"bundleRuntimeLibraryConfiguration"(dependency) {
		dependencyConfiguration()
	}
}

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
