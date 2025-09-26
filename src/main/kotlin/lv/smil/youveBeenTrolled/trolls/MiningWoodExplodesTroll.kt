package lv.smil.youveBeenTrolled.trolls

import lv.smil.youveBeenTrolled.managers.TrollInterface
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class MiningWoodExplodesTroll(private val plugin: JavaPlugin): TrollInterface {
    val listener: Listener? = null

    override fun enable() {
        val l = object : Listener {
            @EventHandler
            fun onBreak(e: BlockBreakEvent) {
                if (!e.block.type.name.contains("LOG")) return
                val block = e.block

                e.isDropItems = false

                block.world.playSound(block.location, Sound.ENTITY_TNT_PRIMED, 1.0f, 1.0f)
                plugin.server.scheduler.runTaskLater(plugin, Runnable {
                    val item = ItemStack(block.type, 1)

                    block.world.createExplosion(block.location, 2.0f)
                    block.world.dropItem(block.location, item)
                }, 40L)
            }
        }

        plugin.server.pluginManager.registerEvents(l, plugin)
    }

    override fun disable() {
        if (listener == null) return

        HandlerList.unregisterAll(listener)
    }
}