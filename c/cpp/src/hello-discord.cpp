// https://yourwaifu.dev/sleepy-discord/docs/reference/indexa_classes
#include "sleepy_discord/sleepy_discord.h"
#include <string.h>
#include <iostream>

std::string token = "token_here";
std::string prefix = "hd!";

class HelloDiscordClient : public SleepyDiscord::DiscordClient {
public:
	using SleepyDiscord::DiscordClient::DiscordClient;
	void onMessage(SleepyDiscord::Message message) override {

    if (message.author.bot || message.serverID.empty())
      return;
      
    // split message content into array of strings
    std::vector<std::string> args;
    std::string s = message.content;

    size_t last = 0;
    size_t next = 0; 

    while ((next = s.find(" ", last)) != std::string::npos) { 
      args.push_back(s.substr(last, next - last));
      last = next + 1;
    }

    args.push_back(s.substr(last));

    // if no arguments were provided, return
    if (args.empty()) {
      return;
    }  

    // extract command
    std::string command = args[0].substr(prefix.size());

    if (command == "ping") {
      reply_to_user(message, "Pong! (Command: " + command + " Args:" + message.content.substr(prefix.size() + command.size()) + ")"); 
    }
    else if (command == "ban")
    {
      // get first mention from message mentions if it is not empty
      std::vector<SleepyDiscord::User> users = message.mentions;
      if (users.empty()) {
        reply_to_user(message, "Please mention a user to ban!");
      }

      SleepyDiscord::ServerMember self = getMember(message.serverID, getID());
      SleepyDiscord::ServerMember author = getMember(message.serverID, message.author);
      SleepyDiscord::ServerMember target = getMember(message.serverID, users[0]);
      
      if (!canInteract(self, target, message.serverID) || !canInteract(author, target, message.serverID)) {
        reply_to_user(message, "I was unable to ban this user, I may not have the required permissions to do this!");
        return;
      }

      SleepyDiscord::BoolResponse result = banMember(message.serverID, target.ID);
      if (result) {
        reply_to_user(message, "Banned " + target.user.username + "!");
      } else {
        reply_to_user(message, "Failed to ban " + target.user.username + "!");
      }
    }
    else if (command == "kick")
    {
      // get first mention from message mentions if it is not empty
      std::vector<SleepyDiscord::User> users = message.mentions;
      if (users.empty()) {
        reply_to_user(message, "Please mention a user to kick!");
      }

      SleepyDiscord::ServerMember self = getMember(message.serverID, getID());
      SleepyDiscord::ServerMember author = getMember(message.serverID, message.author);
      SleepyDiscord::ServerMember target = getMember(message.serverID, users[0]);
      
      if (!canInteract(self, target, message.serverID) || !canInteract(author, target, message.serverID)) {
        reply_to_user(message, "I was unable to kick this user, I may not have the required permissions to do this!");
        return;
      }

      SleepyDiscord::BoolResponse result = kickMember(message.serverID, target.ID);
      if (result) {
        reply_to_user(message, "Kicked " + target.user.username + "!");
      } else {
        reply_to_user(message, "Failed to kick " + target.user.username + "!");
      }
    }
  }

  void reply_to_user(SleepyDiscord::Message message, std::string content) {
    message.reply(this, "<@" + message.author.ID + ">, " + content);
  }

  bool canInteract(SleepyDiscord::ServerMember user, SleepyDiscord::ServerMember target, SleepyDiscord::Snowflake<SleepyDiscord::Server> server) {
    int highestUserRole = getTotalRolePosition(user, server);
    int highestTargetRole = getTotalRolePosition(target, server);

    return (highestUserRole > highestTargetRole);
  }

  int getTotalRolePosition(SleepyDiscord::ServerMember member, SleepyDiscord::Snowflake<SleepyDiscord::Server> server) {
    int totalRolePosition = 0;

    auto roles = getRoles(server).vector();

    for (auto role : roles) {
      for (auto memberRole : member.roles) {
        SleepyDiscord::Snowflake<SleepyDiscord::Role> roleSnowflake = role.ID;
        if (roleSnowflake.number() == memberRole.number()) {
          totalRolePosition += role.position;
        }
      }
    }

    return totalRolePosition;
  }
};

int main() {
	HelloDiscordClient client(token, SleepyDiscord::USER_CONTROLED_THREADS);
	client.run();
}
