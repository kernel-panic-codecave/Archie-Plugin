package net.kernelpanicsoft.archie

import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.util.ModPlatform
import net.kernelpanicsoft.archie.plugin.ModResourcesExtension
import net.kernelpanicsoft.archie.plugin.patchedFMLModType
import org.jetbrains.kotlin.konan.file.file
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Path

val extension = extensions.create<ModResourcesExtension>("modResources")
val loom = extensions.getByType<LoomGradleExtensionAPI>()

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
extension.versions.convention(provider {
	val ret = versionCatalog.versionAliases.associate {
		// both "." and "-" cause issues with expand :/
		it.replace(".", "_") to versionCatalog.findVersion(it).get().requiredVersion
	}
	when (loom.platform.get())
	{
		ModPlatform.FABRIC -> ret.mapValues { (_, version) ->
			version
				.replace(",", " ")
				.replace(Regex("""\s+"""), " ")
				.replace(Regex("""\[(\S+)"""), ">=$1")
				.replace(Regex("""(\S+)\]"""), "<=$1")
				.replace(Regex("""\](\S+)"""), ">$1")
				.replace(Regex("""(\S+)\["""), "<$1")
		}

		else -> ret
	}
})
extension.properties.convention(provider {
	project.properties.mapKeys {
		it.key.replace(".", "_")
	}.mapValues { it.value.toString() }
})

// build logic

tasks.withType<ProcessResources>().configureEach {
	exclude(".cache")

	// allow referencing values from libs.versions.toml in Fabric/Forge mod configs
	val resourceValues = buildMap {
		put("versions", extension.versions.get())
		putAll(extension.properties.get())
	}

	// for incremental builds
	inputs.properties(resourceValues)

	filesMatching(extension.filesMatching.get()) {
		expand(resourceValues)
	}
}



/**
 * `forgeRuntimeLibrary` puts classes on the `MC-BOOTSTRAP` layer, but KotlinForForge puts the Kotlin stdlib on the
 * `PLUGIN` layer. This makes it impossible for libraries loaded via `forgeRuntimeLibrary` to access the Kotlin stdlib.
 * As a workaround, this class implements an artifact transform that adds `FMLModType: GAMELIBRARY` to the jar's
 * `MANIFEST.MF` file. This tells Forge to load this library on the `GAME` layer.
 * Note that this isn't an issue in production since JarJar/included libraries are already put on the `GAME` layer.
 */
abstract class PatchFMLModType : TransformAction<PatchFMLModType.Parameters>
{
	interface Parameters : TransformParameters

	@get:PathSensitive(PathSensitivity.NAME_ONLY)
	@get:InputArtifact
	abstract val inputArtifact: Provider<FileSystemLocation>

	override fun transform(outputs: TransformOutputs)
	{
		val inputFile: File = inputArtifact.get().asFile
		val inputPath: Path = inputFile.toPath()

		val manifest: String = FileSystems.newFileSystem(inputPath).use { fs: FileSystem ->
			fs.file("/META-INF/MANIFEST.MF").bufferedReader().readText()
		}

		if (manifest.contains("FMLModType"))
		{
			inputFile.copyTo(outputs.file(inputFile.name))
			return
		}

		val lf: String = System.lineSeparator()
		val newManifest: String = manifest.trimEnd() + lf + "FMLModType: GAMELIBRARY" + lf

		val outputFile: File = outputs.file(
			inputFile.run { "${nameWithoutExtension}-PatchedFMLModType.${extension}" }.trimEnd('.')
		)

		inputFile.copyTo(outputFile)
		FileSystems.newFileSystem(outputFile.toPath()).use { fs: FileSystem ->
			fs.file("/META-INF/MANIFEST.MF").writeText(newManifest)
		}
	}
}

val artifactType: Attribute<String> = Attribute.of("artifactType", String::class.java)

dependencies {
	attributesSchema {
		attribute(patchedFMLModType)
	}

	artifactTypes.getByName("jar") {
		attributes.attribute(patchedFMLModType, false)
	}

	registerTransform(PatchFMLModType::class) {
		from.attribute(patchedFMLModType, false).attribute(artifactType, "jar")
		to.attribute(patchedFMLModType, true).attribute(artifactType, "jar")
	}

	extensions.add("loom", project.extensions.getByName("loom"))
}

