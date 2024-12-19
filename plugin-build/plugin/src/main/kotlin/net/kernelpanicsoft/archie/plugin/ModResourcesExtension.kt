package net.kernelpanicsoft.archie.plugin

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty

interface ModResourcesExtension
{
	val filesMatching: ListProperty<String>
	val versions: MapProperty<String, String>
	val properties: MapProperty<String, String>
}
