package sh.foxboy.commands

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import sh.foxboy.api.command.Command


class KickCommand(name: String) : Command(name) {
    override fun execute(message: Message, commandLabel: String, args: Array<String?>?) {
        if (message.mentionedMembers.isEmpty()) {
            message.reply("You must mention a user to kick!").queue()
            return
        }

        val sender: Member? = message.guild.getMember(message.author)
        val target: Member = message.mentionedMembers[0]

        if (sender?.hasPermission(Permission.KICK_MEMBERS) != true || !sender.canInteract(target)
            || !message.guild.selfMember.canInteract(target)
            || !message.guild.selfMember.hasPermission(Permission.KICK_MEMBERS)
        ) {
            message.replyFormat(
                "I was unable to kick %s, I may not have the required permissions to do this!",
                target.user.asMention
            ).queue()
            return
        }

        target.kick(args?.joinToString()).queue(
            {
                message.replyFormat(
                    "Kicked %s for reason: %s", target.user.asMention,
                    args?.joinToString()
                )
            }
        ) { message.replyFormat("An error occurred while trying to kick %s", target.user.asMention) }
    }
}