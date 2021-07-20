package sh.foxboy.api.listeners

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import sh.foxboy.api.command.CommandMap


class MessageListener(private val commandMap: CommandMap) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        event.message.reply("Hello!")
    }
}
