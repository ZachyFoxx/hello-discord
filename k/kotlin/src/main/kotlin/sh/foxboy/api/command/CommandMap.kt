package sh.foxboy.api.command

import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import sh.foxboy.api.HelloDiscordBot
import sh.foxboy.api.listeners.MessageListener
import java.util.*


class CommandMap(jdaBot: JDA) {
    private val commands = HashMap<String, Command>()
    private val dotenv = dotenv()

    init {
        jdaBot.addEventListener(MessageListener(this))
    }

    fun registerCommands(vararg commands: Command) {
        commands.forEach { command ->
            println("Registering command \"${command.name}\"...")
            this.commands[command.name] = command
        }
    }

    fun removeCommands(vararg commands: Command) {
        commands.forEach { command ->
            println("Removing command \"${command.name}\" from command registry...")
            this.commands.remove(command.name)
        }
    }

    fun handleCommand(message: Message) {
        if (!message.isFromGuild || message.author.isBot) return

        if (!message.contentRaw.startsWith(dotenv["PREFIX"])) return

        val args: Array<String> = message.contentRaw.substring(dotenv["PREFIX"].length).trim().split(" ").toTypedArray()
        val command: Command = this.commands[args[0]] ?: return

        println("Executing command \"${command.name}\"...")
        command.execute(message, command.name, Arrays.copyOfRange(args, 1, args.size))
    }
}
