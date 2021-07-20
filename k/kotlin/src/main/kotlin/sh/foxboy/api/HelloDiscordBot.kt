package sh.foxboy.api

import net.dv8tion.jda.api.JDABuilder
import sh.foxboy.api.command.CommandMap

class HelloDiscordBot(token: String) {
    val jdaBot = JDABuilder.createDefault(token).build()
    val commandMap = CommandMap(jdaBot)
}
