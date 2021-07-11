package sh.foxboy.api.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sh.foxboy.api.command.CommandMap;

public class MessageListener extends ListenerAdapter {

  private CommandMap commandMap;

  public MessageListener(CommandMap commandMap) {
    super();
    this.commandMap = commandMap;
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    commandMap.handleCommand(event.getMessage());
  }
}
