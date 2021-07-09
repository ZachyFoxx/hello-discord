import { Client } from "./Client";
import { BanCommand } from "./commands/BanCommand";
import { KickCommand } from "./commands/KickCommand";
import { PingCommand } from "./commands/PingCommand";
require("dotenv").config();

// Create a new client
const client = new Client();

// login to Discord
client.login(process.env.TOKEN);

// Register our commands
client.registerCommands(
	new PingCommand(client, "ping"),
	new KickCommand(client, "kick"),
	new BanCommand(client, "ban")
);
