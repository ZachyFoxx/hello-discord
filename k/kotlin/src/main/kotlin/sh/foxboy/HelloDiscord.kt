package sh.foxboy

import io.github.cdimascio.dotenv.dotenv
import sh.foxboy.api.HelloDiscordBot

val dotenv = dotenv()

fun main() {
    val bot = HelloDiscordBot(dotenv["TOKEN"])
}
