package sh.foxboy.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import sh.foxboy.api.command.JDACommand;

public class BanCommand extends JDACommand {

  public BanCommand(String name) {
    super(name);
  }

  @Override
  public void execute(Message message, String commandLabel, String[] args) {

    if (message.getMentionedMembers().isEmpty()) {
      message.reply("You must mention a user to ban!").queue();
      return;
    }

    final Member sender = message.getGuild().getMember(message.getAuthor());
    final Member target = message.getMentionedMembers().get(0);

    if (!sender.hasPermission(Permission.BAN_MEMBERS) || !sender.canInteract(target)
        || !message.getGuild().getSelfMember().canInteract(target)
        || !message.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
      message.replyFormat("I was unable to ban %s, I may not have the required permissions to do this!",
          target.getUser().getAsMention()).queue();
      return;
    }

    target.ban(0).queue(
        (__) -> message.replyFormat("Kicked %s for reason: %s", target.getUser().getAsMention(),
            String.join(" ", args)),
        (error) -> message.replyFormat("An error occurred while trying to ban %s", target.getUser().getAsMention())

    );
  }

}
