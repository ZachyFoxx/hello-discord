package sh.foxboy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Properties;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import sh.foxboy.api.command.CommandMap;
import sh.foxboy.api.listeners.MessageListener;
import sh.foxboy.command.BanCommand;
import sh.foxboy.command.KickCommand;
import sh.foxboy.command.PingCommand;

public class HelloDiscord {

    public static String token;
    public static String prefix;

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        String fileName = "hd.config";

        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
        } catch (FileNotFoundException ex) {
            new File("hd.config").createNewFile();
            props.setProperty("hd.token", "xxxx");
            props.setProperty("hd.prefix", "hd!");
            FileWriter writer = new FileWriter(fileName);
            props.store(writer, "Hello-Discord Java configuration");
            writer.close();
            System.out.println(
                    "No configuration found! One has been generated please configure and relaunch application");
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("There was an issue while trying to read the configuration file!");
            return;
        }

        token = props.getProperty("hd.token");
        prefix = props.getProperty("hd.prefix");

        JDA discordApi = JDABuilder.createDefault(token).build();
        CommandMap commandMap = new CommandMap(discordApi);
        discordApi.addEventListener(new MessageListener(commandMap));

        commandMap.registerCommands(new PingCommand("ping"), new KickCommand("kick"), new BanCommand("ban"));

        System.out.println("Connecting to Discord...");
        discordApi.awaitReady();
        System.out.println("Connected to Discord as " + discordApi.getSelfUser().getAsTag() + "...");

    }
}
