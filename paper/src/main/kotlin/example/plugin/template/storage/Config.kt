package example.plugin.template.storage

import dev.dejvokep.boostedyaml.YamlDocument

object Config : AbstractConfig("config.yml") {

    data class Values(
        val configVersion: Int
    )

    @Volatile
    var values: Values = Values(
        configVersion =  1
    )
        private set

    override fun loadValues(doc: YamlDocument) {
        values = Values(
            configVersion = doc.getInt("config-version")
        )
    }
}
