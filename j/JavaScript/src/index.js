// This script was *mostly* generated using GitHub CoPilot!
// https://copilot.github.com/

const Discord = require('discord.js');
const client = new Discord.Client();
require('dotenv').config()

// get prefix from env file
const prefix = process.env.PREFIX || '!';
const token = process.env.TOKEN;

// log ready status with bot we are logged in as 
client.on('ready', () => {
    console.log(`Logged in as ${client.user.tag}`);
});

// client on message event
client.on('message', message => {
  // if message is from a bot or not from a guild, return
  if (message.author.bot || !message.guild) return;

  // if message does not start with prefix, return
  if (!message.content.startsWith(prefix)) return;

  // get arguments from message
  const args = message.content.slice(prefix.length).split(' ');
  // get command from message
  const command = args.shift().toLowerCase();

  // ping command
  if (command === 'ping') {
    // log command
    console.log(`${message.author.tag} ran command: ${command}`);

    // send pong with command and args
    message.channel.send(`Pong! ${args.join(' ')}`);

    // log response to console
    console.log(`Response: ${args.join(' ')}`);
  }

  // kick command
  if (command === 'kick') {
    // log command and arguments
    console.log(`${message.author.tag} ran command: ${command} ${args.join(' ')}`);

    // get member from message
    const member = message.guild.member(message.mentions.users.first());

    // if member is not found, return
    if (!member) return;

    // if member is not in guild, return
    if (!member.guild.member(message.member)) return;

    // get reason
    const reason = args.join(' ').slice(prefix.length); // remove prefix from reason

    // kick member with reason, if error assume insuffient permissions
    member.kick(reason)
      .then(() => {
        // log response to console
        console.log(`Response: Kicked ${member.user.tag}`);
        // send message to channel
        message.channel.send(`Kicked ${member.user.tag}`);

        // tell kicked member the guild they were kicked from and the reason
        member.user.send(`You were kicked from ${message.guild.name} by ${message.author.tag} for: ${reason}`);
      }).catch(() => {  
        // log response to console
        console.log(`Response: Insufficient permissions to kick ${member.user.tag}`);
        // reply to user with error
        message.reply('Insufficient permissions to kick this member.');
      })
  }

  // ban command, this is similar to kick
  if (command === 'ban') {
    // log command and arguments
    console.log(`${message.author.tag} ran command: ${command} ${args.join(' ')}`);

    // get member from message
    const member = message.guild.member(message.mentions.users.first());

    // if member is not found, return
    if (!member) return;

    // if member is not in guild, return
    if (!member.guild.member(message.member)) return;

    // get reason
    const reason = args.join(' ').slice(prefix.length); // remove prefix from reason

    // ban member with reason, if error assume insuffient permissions
    member.ban(reason)
      .then(() => {
        // log response to console
        console.log(`Response: Banned ${member.user.tag}`);
        // send message to channel
        message.channel.send(`Banned ${member.user.tag}`);

        // tell banned member the guild they were banned from and the reason
        member.user.send(`You were banned from ${message.guild.name} by ${message.author.tag} for: ${reason}`);
      }).catch(() => {  
        // log response to console
        console.log(`Response: Insufficient permissions to ban ${member.user.tag}`);
        // reply to user with error
        message.reply('Insufficient permissions to ban this member.');
      })
  }
});

//login
client.login(token);
