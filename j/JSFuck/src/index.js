import { Client, Collection } from "discord.js";
import { config } from "dotenv";
config();
const client = new Client();
client.commands = new Collection();

const prefix = process.env.PREFIX;
const token = process.env.TOKEN;

client.on("ready", () => {
	console.log(`Logged in as ${client.user.tag}!`);
});

client.on("message", (message) => {
	// If this message is not from a guild or if it is from a bot, we ignore it.
	if (!message.guild || message.author.bot) return;

	// If this message is not for our bot, we ignore it.
	if (!message.cleanContent.startsWith(prefix)) return;

	// Get the args and command name
	const args = message.content.slice(prefix.length).trim().split(/ +/);
	const command = args.shift()?.toLowerCase();

	if (command == "ping") {
		message.reply(`Pong! (Command: ${command}, args: ${args.join(" ")}}`);
	}

	if (command == "kick") {
		const user = message.mentions.users.first();

		if (!user) {
			message.reply("You did not mention a user to kick!");
		}

		const member = message.guild.member(user);

		if (!member) {
			message.reply("That user is not in this guild!");
		}

		const reason = args.slice(1).join(" ");
		member
			.kick(args.join(" "))
			.then(() => {
				message.reply(`Kicked ${user} for reason: ${reason}`);
			})
			.catch(() => {
				message.reply(
					`I was unable to kick ${user}, I may not have the required permissions to do this!`
				);
			});
		return;
	}

	if (command == "ban") {
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
});

client.login(token);
