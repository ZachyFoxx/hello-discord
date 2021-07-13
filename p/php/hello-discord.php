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
        $guild = $discord->guilds->get('id', $message->guild_id);

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
        $command = substr($args[0], strlen($prefix));
        $args = array_slice($args, strlen($prefix));

        echo $command,PHP_EOL;

        // ping command
        if ($command === 'ping') {
          // reply with Pong! and message arguments
          $message->reply("Pong! ${implode(" ", $args)}");
        }

        // ban command
        if ($command === 'ban') {
          $mentioned = $message->mentions->first();
          $id = $mentioned->id ?: "";
          
          $guild->members->fetch($id)->done(
            function ($member) use ($guild, $message) {
              $member->ban(0, implode(" ", $args))->done(
              function () use ($message, $member) {
                $message->reply("Banned {$member->username}!");
                },
                function ($error) use ($message) {
                  $message->reply("I do not have the required permissions to ban this user!");
                }
              );
            },
            function ($error) use ($message) {
              $message->reply("Please mention a member to ban!");
              return;
            }
          );
        }

        // kick command
        if ($command === 'kick') {
          $mentioned = $message->mentions->first();
          $id = $mentioned->id ?: "";

          // get first mentioned member from collection from message
          $guild->members->fetch($id)->done(
          function ($member) use ($guild, $message) {
              $guild->members->kick($member)->done(
                function () use ($message, $member) {
                  $message->reply("Kicked {$member->username}!");
                },
                function ($error) use ($message) {
                  $message->reply("I do not have the required permissions to kick this user!");
                }
              );
            },
            function ($error) use ($message) {
              $message->reply("Please mention a member to kick!");
              return;
            }
          );
        }

    });
});

$discord->run();
