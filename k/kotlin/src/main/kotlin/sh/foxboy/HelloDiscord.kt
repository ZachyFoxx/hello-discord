package sh.foxboy

import io.github.cdimascio.dotenv.dotenv
import sh.foxboy.api.HelloDiscordBot
import sh.foxboy.commands.BanCommand
import sh.foxboy.commands.KickCommand
import sh.foxboy.commands.PingCommand


fun main() {
    val dotenv = dotenv()
    val bot = HelloDiscordBot(dotenv["TOKEN"])
    bot.registerCommands(PingCommand("ping"), KickCommand("kick"), BanCommand("ban"))
}
