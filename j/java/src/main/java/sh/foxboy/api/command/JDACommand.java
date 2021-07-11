package sh.foxboy.api.command;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;

public abstract class JDACommand {

  @Getter
  String name;

  public JDACommand(String name) {
    this.name = name;
  }

  /**
   * Executes a command
   * 
   * @param message      The message received
   * @param commandLabel command used
   * @param args         The arguments received
   */
  public abstract void execute(Message message, String commandLabel, String[] args);

}
