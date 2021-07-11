package sh.foxboy.command;

import net.dv8tion.jda.api.entities.Message;
import sh.foxboy.api.command.JDACommand;

public class PingCommand extends JDACommand {

  public PingCommand(String name) {
    super(name);
  }

  @Override
  public void execute(Message message, String commandLabel, String[] args) {
    message.getChannel().sendMessage(message.getAuthor().getAsMention() + " Pong! (Command: " + commandLabel
        + ", Args: " + String.join(" ", args) + ")").queue();
  }

}
