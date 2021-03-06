package com.mineinabyss.idofront.commands.execution

import com.mineinabyss.idofront.commands.CommandHolder
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
annotation class ExperimentalCommandDSL

/**
 * Manages linking spigot's [CommandExecutor.onCommand] events to a [CommandHolder] inside
 */
@ExperimentalCommandDSL
abstract class IdofrontCommandExecutor : CommandExecutor {
    abstract val commands: CommandHolder

    /** Gets the command or send the player a message if it isn't found */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        commands.execute(command.name,
                sender,
                args.toList())
        return true
    }

    /** The starting block for the command DSL. */
    fun commands(plugin: JavaPlugin, init: CommandHolder.() -> Unit): CommandHolder {
        val commandHolder = CommandHolder(plugin, this)
        commandHolder.init()
        return commandHolder
    }
}