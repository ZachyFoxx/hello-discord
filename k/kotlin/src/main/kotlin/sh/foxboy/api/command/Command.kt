package sh.foxboy.api.command

import net.dv8tion.jda.api.entities.Message


abstract class Command(name: String) {
    val name = name

    /**
     * Executes a command
     *
     * @param message      The message received
     * @param commandLabel command used
     * @param args         The arguments received
     */
    abstract fun execute(message: Message, commandLabel: String, args: Array<String?>?)
}