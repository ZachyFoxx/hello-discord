package sh.foxboy.commands

import net.dv8tion.jda.api.entities.Message
import sh.foxboy.api.command.Command

class PingCommand(name: String) : Command(name) {
    override fun execute(message: Message, commandLabel: String, args: Array<String?>?) {
        message.reply("Pong! (Command: ${commandLabel}, Args: ${args?.joinToString()})").queue()
    }
}