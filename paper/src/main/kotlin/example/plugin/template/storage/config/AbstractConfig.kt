package example.plugin.template.storage.config

import dev.dejvokep.boostedyaml.YamlDocument
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings
import java.io.File

abstract class AbstractConfig(private val fileName: String) {

    private lateinit var doc: YamlDocument

    fun init(dataFolder: File) {
        val defaults = javaClass.classLoader.getResourceAsStream(fileName)
            ?: error("$fileName is missing from the jar!")

        dataFolder.mkdirs()

        doc = YamlDocument.create(
            File(dataFolder, fileName),
            defaults,
            GeneralSettings.DEFAULT,
            LoaderSettings.builder().setAutoUpdate(true).build(),
            DumperSettings.DEFAULT,
            UpdaterSettings.builder()
                .setVersioning(BasicVersioning("config-version"))
                .build()
        )

        loadValues(doc)
    }

    fun reload(): Boolean {
        if (!::doc.isInitialized) return false
        val ok = doc.reload()
        if (ok) loadValues(doc)
        return ok
    }

    protected fun get(): YamlDocument {
        check(::doc.isInitialized) { "Config not initialized. Call init(dataFolder) first." }
        return doc
    }

    protected abstract fun loadValues(doc: YamlDocument)
}
