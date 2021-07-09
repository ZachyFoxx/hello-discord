import { Client as DiscordClient } from "discord.js";
import { Command } from "./lib/commands/Command";
import { CommandMap } from "./lib/commands/CommandMap";

export class Client extends DiscordClient {
	readonly commandMap = new CommandMap(this);

	/**
	 * A simple wrapper client for discord.js
	 */
	constructor() {
		super();

		this.on("ready", () => {
			console.log(`Logged in as ${this.user.tag}!`);
		});
	}

	/**
	 * Register one or many commands
	 * @param commands to register
	 */
	async registerCommands(...commands: Command[]) {
		this.commandMap.registerCommands(...commands);
	}

	/**
	 * Login to Discord
	 * @param token for this bot
	 * @returns the token as a promise
	 */
	async login(token: string): Promise<string> {
		console.log("Logging into Discord...");
		await super.login(token);
		return token;
	}
}
