package lv.smil.youveBeenTrolled

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import lv.smil.youveBeenTrolled.commands.TrollCommand
import lv.smil.youveBeenTrolled.managers.TrollManager
import org.bukkit.plugin.java.JavaPlugin

class YouveBeenTrolled : JavaPlugin() {
    // Variables
    companion object {
        var instance: YouveBeenTrolled? = null
        var trollman: TrollManager? = null
    }

    // ...
    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this))
    }

    override fun onEnable() {
        instance = this

        // init manager & commands
        trollman = TrollManager(this)
        trollman?.registerTrolls()
        TrollCommand(this)

        CommandAPI.onEnable()
    }

    override fun onDisable() {
        CommandAPI.onDisable()
    }
}
