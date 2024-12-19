package net.kernelpanicsoft.archie.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class ArchiePluginTest {
    @JvmField
    @Rule
    var testProjectDir: TemporaryFolder = TemporaryFolder()

    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("net.kernelpanicsoft.archie.plugin")

        assert(project.tasks.getByName("modResources") is ModResourcesExtension)
    }

    @Test
    fun `extension modResources is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("net.kernelpanicsoft.archie.plugin")

        assertNotNull(project.extensions.getByName("modResources"))
    }
}
