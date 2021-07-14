#include "sleepy_discord/sleepy_discord.h"
#include <string.h>

std::string token = "token_here";
std::string prefix = "hd!";

class HelloDiscordClient : public SleepyDiscord::DiscordClient {
public:
	using SleepyDiscord::DiscordClient::DiscordClient;
	void onMessage(SleepyDiscord::Message message) override {

    if (message.author.bot || message.serverID == nullptr)
      return;

    // if message does not start with a prefix, return
    if (!message.startsWith(prefix))
      return;

    // split message into arguments
    char * args_temp;
    args_temp = strtok (message.content.c_str(), " ");
    std::vector<std::string> args;

    while (args_temp != NULL) {
      args.push_back(args_temp);
      args_temp = strtok (NULL, " ");
    }

    std::string command = args[0].substr(prefix.size());

    if (command == "hello") {
      SleepyDiscord::Message reply = SleepyDiscord::Message(message.author, "Hello " + message.author.username + "!");
      sendMessage(message.channelID, reply);
    }

    if (message.startsWith("whcg hello"))
			sendMessage(message.channelID, "Hello " + message.author.username);
	}
};


int main() {
	HelloDiscordClient client(token, SleepyDiscord::USER_CONTROLED_THREADS);
	client.run();
}
