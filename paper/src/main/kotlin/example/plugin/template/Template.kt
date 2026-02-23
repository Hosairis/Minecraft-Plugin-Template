package example.plugin.template

import example.plugin.template.storage.Config
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

class Template: JavaPlugin() {
    
    companion object {
        private lateinit var instance: Template

        fun getInst(): Template {
            return instance
        }
    }
    private var metrics: Metrics? = null

    // Called when the plugin is enabled (initialize resources, register events/commands).
    override fun onEnable() {
        instance = this

        Config.init(this.dataFolder)

        metrics = Metrics(getInst(), 123) // Remember to change the plugin ID
    }

    // Called when the plugin is disabled (cleanup resources, save data).
    override fun onDisable() {
        metrics?.shutdown()
    }
}