# Thanks @Nanofaux!
# (and GitHub Copilot)

import discord
from discord.ext import commands
import os
from dotenv import load_dotenv

load_dotenv()

# to access env vars do it like
env = os.environ.get('key')
bot = commands.Bot(command_prefix=os.environ['PREFIX'])


#kick command with reason argument
@bot.command()
@commands.has_permissions(kick_members=True)
@commands.bot_has_permissions(kick_members=True)
async def kick(ctx, user: discord.Member, *, reason: commands.clean_content = '[No reason given]'): 
    """ Kick a member for being bad """
    author = ctx.author
    guild = ctx.guild
    if user.top_role.position >= ctx.guild.me.top_role.position:
        return await ctx.send("I do not have the required permissions to kick this member.")
    elif user.top_role.position >= author.top_role.position:
        return await ctx.send("I do not have the required permissions to kick this member.")
    try:
        await user.send(f"You were kicked from {guild}, reason: {reason}")
    except discord.Forbidden:
        pass
    await user.kick(reason=f"By {author}, reason: {reason}")


#ban command with reason argument
@bot.command()
@commands.has_permissions(ban_members=True)
@commands.bot_has_permissions(ban_members=True)
async def ban(ctx, user: discord.User, *, reason: commands.clean_content = '[No reason given]'): 
    """ Ban a member for being badd """
    # check if bot has permissions to kick user
    author = ctx.author
    guild = ctx.guild
    if member := guild.get_member(user.id):
        if member.top_role.position >= ctx.guild.me.top_role.position:
            return await ctx.send("I do not have the required permissions to ban this member.")
        elif member.top_role.position >= author.top_role.position:
            return await ctx.send("I do not have the required permissions to ban this member.")
    else:
        # They aren't in the guild obv
        try:
            await guild.fetch_ban(user)
        except discord.NotFound:
            try:
                await user.send(f"You were banned from {guild}, reason: {reason}")
            except discord.Forbidden:
                pass
            await guild.ban(user, reason=f"By {author}, reason: {reason}")
            await ctx.send(f"User {user} has been banned. Reason: {reason}")
        else:
            await ctx.send("That user was already banned from the guild!")


# Ping command
@bot.command()
async def ping(ctx):
    """ Check if bot is online """
    await ctx.send(f"Pong! {round(bot.latency * 1000)}ms")

bot.run(os.environ['TOKEN'])
