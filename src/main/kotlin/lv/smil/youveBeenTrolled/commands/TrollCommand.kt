package lv.smil.youveBeenTrolled.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.ListArgumentBuilder
import dev.jorel.commandapi.arguments.MultiLiteralArgument
import dev.jorel.commandapi.executors.CommandExecutor
import lv.smil.youveBeenTrolled.YouveBeenTrolled.Companion.trollman
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

class TrollCommand(var plugin: JavaPlugin) {
    init {
        CommandAPICommand("troll")
            .withAliases("lol", "prank")
            .withPermission("troll.command")
            .withArguments(MultiLiteralArgument("actions", "enable", "disable", "list"))
            .withOptionalArguments(
                ListArgumentBuilder<String>("trolls")
                    .withList(trollman?.listAllTrolls() ?: emptyList())
                    .withMapper { it }
                    .buildText()
            )
            .executes(CommandExecutor { sender, args ->
                when (args.get("actions")) {
                    "enable" -> {
                        var execute: String? = trollman?.enableSpecificTroll(args.get("trolls") as String)
                        sender.sendMessage(Component.text("<red>${execute}"))
                    }
                    "disable" -> {
                        var execute: String? = trollman?.disableSpecificTroll(args.get("trolls") as String)
                        sender.sendMessage(Component.text("<red>${execute}"))
                    }
                    "list" -> {
                        var list = Component.text("\n<yellow>Available trolls:\n")

                        for (troll in trollman?.listAllTrolls()!!) {
                            list = list.append(Component.text("<green>${troll}\n"))
                        }

                        sender.sendMessage(list)
                    }
                }
            }).register()
    }
}