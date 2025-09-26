package lv.smil.youveBeenTrolled.managers

import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections

class TrollManager(val plugin: JavaPlugin) {
    // variables & values
    companion object {
        val trolls: MutableMap<String, TrollInterface> = mutableMapOf()
    }

    // #################################################################################################
    // #################################################################################################
    // #################################################################################################
    // init, register - uses Reflections
    fun registerTrolls() {
        val ref = Reflections("lv.smil.youveBeenTrolled.trolls")
        val classes = ref.getSubTypesOf(TrollInterface::class.java)

        classes.map { clazz ->
            try {
                val instance = clazz.getDeclaredConstructor().newInstance(plugin)
                instance.init()
                instance.disable()

                plugin.logger.info("Registered troll: ${clazz.simpleName}")
                trolls.put(clazz.simpleName, instance)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // #################################################################################################
    // #################################################################################################
    // #################################################################################################
    // management
    fun disableSpecificTroll(troll: String): String {
        try {
            trolls.mapNotNull {
                if (it.key == troll) {
                    it.value.disable()
                }
            }
            return "Troll $troll disabled!"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error disabling troll!"
        }
    }

    fun enableSpecificTroll(troll: String): String {
        try {
            trolls.mapNotNull {
                if (it.key == troll) {
                    it.value.enable()
                }
            }
            return "Troll $troll enabled!"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error enabling troll!"
        }
    }

    fun listAllTrolls(): List<String> {
        try {
            trolls.map {
                return trolls.keys.toList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf("Error listing trolls!")
        }
        return listOf("No trolls found!")
    }
}