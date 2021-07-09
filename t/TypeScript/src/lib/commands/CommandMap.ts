import { Message } from "discord.js";
import { Client } from "../../Client";
import { Command } from "./Command";

/**
 * Map of commands
 */
export class CommandMap {
	// get our bot's prefix
	prefix: string = process.env.PREFIX;

	constructor(client: Client) {
		client.on("message", (message) => this.handleCommand(message));
	}

	readonly commands = new Map<string, Command>();

	handleCommand(message: Message) {
		// If this message is not from a guild or if it is from a bot, we ignore it.
		if (!message.guild || message.author.bot) {
			return;
		}

		// Make sure it starts with our prefix
		if (!message.cleanContent.startsWith(this.prefix)) {
			return;
		}

		// Get the args and command name
		const args = message.content
			.slice(this.prefix.length)
			.trim()
			.split(/ +/);
		const command = this.getCommand(args.shift()?.toLowerCase());

		// If the command is not registered we simply ignore it
		if (!command) {
			return;
		}

		// Finally we execute our command and provide it with the necessary parameters
		command.execute(message, command.name, ...args);
	}

	/**
	 * Get a command from the CommandMap
	 * @param name of command to get
	 */
	getCommand(name: string) {
		return this.commands.get(name);
	}

	/**
	 * Register one or many commands
	 * @param commands to register
	 */
	registerCommands(...commands: Command[]): this {
		commands.forEach((command: Command) => {
			console.log(`Registering command ${command.name}...`);
			this.commands.set(command.name, command);
		});
		return this;
	}
}
