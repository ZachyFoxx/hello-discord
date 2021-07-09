import { Message } from "discord.js";

import { Client } from "../../Client";

/**
 * Constructs a new command
 */
export abstract class Command {
	readonly name: string;
	constructor(readonly client: Client, name: string) {
		this.name = name;
	}

	/**
	 * Execute this command
	 * @param message received from Discord
	 * @param commandName name of this command
	 * @param args provided by user input
	 */
	abstract execute(
		message: Message,
		commandName: string,
		...args: string[]
	): Promise<any>;
}
