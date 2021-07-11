package sh.foxboy.api.command;

import java.util.Arrays;
import java.util.HashMap;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import sh.foxboy.HelloDiscord;

/**
 * Map of commands
 */
public class CommandMap {
  JDA discordApi;

  public CommandMap(JDA discordApi) {
    this.discordApi = discordApi;
  }

  @Getter
  HashMap<String, JDACommand> commands = new HashMap<String, JDACommand>();

  /**
   * Register one or many commands
   * 
   * @param commands to register
   */
  public void registerCommands(JDACommand... commands) {
    for (JDACommand command : commands) {
      System.out.println("Registering command " + command.getName() + "...");
      this.commands.put(command.getName(), command);
    }
  }

  /**
   * Extract and execute a command from a {@link Message}
   * 
   * @param message
   */
  public void handleCommand(Message message) {

    if (!message.isFromGuild() || message.getAuthor().isBot())
      return;

    if (!message.getContentRaw().startsWith(HelloDiscord.prefix))
      return;

    String[] args = message.getContentRaw().substring(HelloDiscord.prefix.length()).trim().split(" ");
    JDACommand command = this.getCommand(args[0]);

    if (command == null)
      return;

    command.execute(message, command.getName(), Arrays.copyOfRange(args, 1, args.length));
  }

  private JDACommand getCommand(String name) {
    return this.commands.get(name);
  }

}
