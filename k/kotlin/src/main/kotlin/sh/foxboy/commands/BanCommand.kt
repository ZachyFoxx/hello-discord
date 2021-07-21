package sh.foxboy.commands

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import sh.foxboy.api.command.Command


class BanCommand(name: String) : Command(name) {
    override fun execute(message: Message, commandLabel: String, args: Array<String?>?) {
        if (message.mentionedMembers.isEmpty()) {
            message.reply("You must mention a user to ban!").queue()
            return
        }

        val sender: Member? = message.guild.getMember(message.author)
        val target: Member = message.mentionedMembers[0]

        if (sender?.hasPermission(Permission.BAN_MEMBERS) != true || !sender.canInteract(target)
            || !message.guild.selfMember.canInteract(target)
            || !message.guild.selfMember.hasPermission(Permission.BAN_MEMBERS)
        ) {
            message.replyFormat(
                "I was unable to ban %s, I may not have the required permissions to do this!",
                target.user.asMention
            ).queue()
            return
        }

        target.ban(0).queue(
            {
                message.replyFormat(
                    "Kicked %s for reason: %s", target.user.asMention,
                    args?.joinToString()
                )
            }
        ) { message.replyFormat("An error occurred while trying to ban %s", target.user.asMention) }
    }
}