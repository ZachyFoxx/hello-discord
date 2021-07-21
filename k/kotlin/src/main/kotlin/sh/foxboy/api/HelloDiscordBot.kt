package sh.foxboy.api

import net.dv8tion.jda.api.JDABuilder
import sh.foxboy.api.command.Command
import sh.foxboy.api.command.CommandMap
import sh.foxboy.api.listeners.MessageListener

class HelloDiscordBot(token: String) {
    val jdaBot = JDABuilder.createDefault(token).build()
    val commandMap = CommandMap(jdaBot)


    init {
        println("Connecting to Discord...")
        jdaBot.awaitReady()
        println("Connected to Discord as ${jdaBot.selfUser.asTag}")
    }
    /**
     * This method will block until JDA has reached the status {@link Status#CONNECTED}.
     * <br>This status means that JDA finished setting up its internal cache and is ready to be used.
     *
     * @throws InterruptedException
     *         If this thread is interrupted while waiting
     * @throws IllegalStateException
     *         If JDA is shutdown during this wait period
     *
     * @return The current JDA instance, for chaining convenience
     */
    fun awaitReady() {
        jdaBot.awaitReady()
    }

    fun registerCommands(vararg commands: Command) {
        commandMap.registerCommands(*commands)
    }
}
