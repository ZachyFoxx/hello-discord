import { Message } from "discord.js";
import { Command } from "../lib/commands/Command";

export class BanCommand extends Command {
	execute(
		message: Message,
		commandName: string,
		...args: string[]
	): Promise<any> {
		const user = message.mentions.users.first();

		if (!user) {
			message.reply("You did not mention a user to ban!");
		}

		const member = message.guild.member(user);

		if (!member) {
			message.reply("That user is not in this guild!");
		}

		const reason = args.slice(1).join(" ");
		member
			.ban()
			.then(() => {
				message.reply(`Banned ${user} for reason: ${reason}`);
			})
			.catch(() => {
				message.reply(
					`I was unable to ban ${user}, I may not have the required permissions to do this!`
				);
			});
		return;
	}
}
