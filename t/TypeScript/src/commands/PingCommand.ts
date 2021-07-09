import { Message } from "discord.js";
import { Command } from "../lib/commands/Command";

export class PingCommand extends Command {
	execute(
		message: Message,
		commandName: string,
		...args: string[]
	): Promise<any> {
		message.reply(
			`Pong! (Command: ${commandName}, args: ${args.join(" ")}}`
		);
		return;
	}
}
