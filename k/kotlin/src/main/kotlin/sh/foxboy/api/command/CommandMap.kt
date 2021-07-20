package sh.foxboy.api.command

import net.dv8tion.jda.api.JDA
import sh.foxboy.api.listeners.MessageListener

class CommandMap(jdaBot: JDA) {
    val commands = HashMap<String, Command>()

    init {
        jdaBot.addEventListener(MessageListener(this))
    }


}
