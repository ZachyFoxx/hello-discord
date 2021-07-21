local discordia = require('discordia')
local client = discordia.Client()

local token = "tokenhere"
prefix = "hd!"

client:on('ready', function()
	print('Logged in as '.. client.user.username)
end)

client:on('messageCreate', function(message)
  if message.author.isBot or message.guild == nil then return end

  -- check if message starts with prefix
  if not string.starts(message.content, prefix) then return end
	
	local args = Split(message.content, " ") -- split message into a table
	local command = string.sub(args[1], string.len(prefix)+1) -- remove prefix from command
	local argsStr = string.sub(message.content, string.len(prefix..command)+1)

	if (command == "ping") then
		message:reply("Pong! (Command: " .. command .. ", Args:" .. argsStr .. ")")
	end

	if (command == "kick") then
		local author = message.guild:getMember(message.author.id)
    local member = message.mentionedUsers.first

		if not member then
			message:reply("You need to mention a user to kick.")
		else
			target = message.guild:getMember(member.id)
      if author.highestRole.position > target.highestRole.position then
        target:kick(argsStr)
			else
				message:reply("You can't kick someone with a higher role than you.")
			end
		end
	end

		if (command == "ban") then
		local author = message.guild:getMember(message.author.id)
    local member = message.mentionedUsers.first

		if not member then
			message:reply("You need to mention a user to ban.")
		else
			target = message.guild:getMember(member.id)
      if author.highestRole.position > target.highestRole.position then
        target:ban()
			else
				message:reply("You can't ban someone with a higher role than you.")
			end
		end
	end


end)

client:run("Bot " .. token)

-- check if message starts with 
function string.starts(String,Start)
   return string.sub(String,1,string.len(Start))==Start
end

-- Split a string into an array of words
function Split(s, delimiter)
    result = {};
    for match in (s..delimiter):gmatch("(.-)"..delimiter) do
        table.insert(result, match);
    end
    return result;
end
