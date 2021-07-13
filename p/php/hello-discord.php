<?php
require __DIR__ . '/vendor/autoload.php';

use Discord\Discord;

$dotenv = Dotenv\Dotenv::createImmutable(__DIR__);
$dotenv->load();

$GLOBALS['token'] = $_ENV['TOKEN'];
$GLOBALS['prefix'] = $_ENV['PREFIX'];

$discord = new Discord([
    'token' => $token,
]);

$discord->on('ready', function ($discord) {
    echo "Bot is ready!", PHP_EOL;

    // Listen for messages.
    $discord->on('message', function ($message, $discord) {
        $prefix = $GLOBALS['prefix']; // what even is this? it's the meaning of life, the universe, and everything; 42

        echo "{$message->author->username}: {$message->content}",PHP_EOL;

        // if message is from a bot odr not in a guild, return
        if ($message->author->bot) {
            return;
        }

        // if message does not start with $prefix, return
        if (substr($message->content, 0, strlen($prefix)) !== $prefix) {
            return;
        }
        
        // get arguments from message
        $args = explode(" ", $message->content);
        $command = substr($args[0], -strlen($prefix)-1, strlen($prefix)+1);
        $args = array_slice($args, strlen($prefix));

        // ping command
        if ($command === 'ping') {
          // reply with Pong! and message arguments
          $message->reply("Pong! ${implode("", $args)}");
        }

        // kick command
        if ($command === 'kick') {
          // get first mentioned member from collection from message
          $member = $message->mentions->first();
          if ($member == null) {
            $message->reply("Please mention a member to kick!");
            return;
          }

          try {
            echo $member->username;
            $guild = $discord->guilds->get($message->channel->guild->id,null);

            // this changes member from the mentioned member to the bot???
            // TODO: fix this shit it's 2:30 and I want to go to sleep.
            $member = $guild->members->get($member, null);

            echo $member->username;

            $guild->members->kick($member);
            $message->reply("Kicked {$member->username}!");

          } catch (Exception $e) {
              $message->reply("Failed to kick {$member->username}!");
          }
        }

    });
});

$discord->run();
