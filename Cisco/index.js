//Webex Bot Starter - featuring the webex-node-bot-framework - https://www.npmjs.com/package/webex-node-bot-framework

var framework = require('webex-node-bot-framework');
var webhook = require('webex-node-bot-framework/webhook');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();
app.use(bodyParser.json());
app.use(express.static('images'));
const config = require("./config.json");
const { ToadScheduler, SimpleIntervalJob, Task } = require('toad-scheduler')
const scheduler = new ToadScheduler()

var data = {}

// init framework
var framework = new framework(config);
framework.start();
console.log("Starting framework, please wait...");

framework.on("initialized", function () {
  console.log("framework is all fired up! [Press CTRL-C to quit]");
});

// A spawn event is generated when the framework finds a space with your bot in it
// If actorId is set, it means that user has just added your bot to a new space
// If not, the framework has discovered your bot in an existing space
framework.on('spawn', (bot, id, actorId) => {
  if (!actorId) {
    // don't say anything here or your bot's spaces will get
    // spammed every time your server is restarted
    console.log(`While starting up, the framework found our bot in a space called: ${bot.room.title}`);
  } else {
    // When actorId is present it means someone added your bot got added to a new space
    // Lets find out more about them..
    var msg = 'You can say `help` to get the list of words I am able to respond to.';
    bot.webex.people.get(actorId).then((user) => {
      msg = `Hello there ${user.displayName}. ${msg}`; 
    }).catch((e) => {
      console.error(`Failed to lookup user details in framwork.on("spawn"): ${e.message}`);
      msg = `Hello there. ${msg}`;  
    }).finally(() => {
      // Say hello, and tell users what you do!
      if (bot.isDirect) {
        bot.say('markdown', msg);
      } else {
        let botName = bot.person.displayName;
        msg += `\n\nDon't forget, in order for me to see your messages in this group space, be sure to *@mention* ${botName}.`;
        bot.say('markdown', msg);
      }
    });
  }
});


//Process incoming messages

let responded = false;
/* On mention with command
ex User enters @botname help, the bot will write back in markdown
*/
framework.hears(/help|what can i (do|say)|what (can|do) you do/i, function (bot, trigger) {
  console.log(`someone needs help! They asked ${trigger.text}`);
  responded = true;
  bot.say(`Hello ${trigger.person.displayName}.`)
    .then(() => sendHelp(bot))
    .catch((e) => console.error(`Problem in help hander: ${e.message}`));
});

/* On mention with command
ex User enters @botname framework, the bot will write back in markdown
*/
framework.hears('framework', function (bot) {
  console.log("framework command received");
  responded = true;
  bot.say("markdown", "The primary purpose for the [webex-node-bot-framework](https://github.com/jpjpjp/webex-node-bot-framework) was to create a framework based on the [webex-jssdk](https://webex.github.io/webex-js-sdk) which continues to be supported as new features and functionality are added to Webex. This version of the project was designed with two themes in mind: \n\n\n * Mimimize Webex API Calls. The original flint could be quite slow as it attempted to provide bot developers rich details about the space, membership, message and message author. This version eliminates some of that data in the interests of efficiency, (but provides convenience methods to enable bot developers to get this information if it is required)\n * Leverage native Webex data types. The original flint would copy details from the webex objects such as message and person into various flint objects. This version simply attaches the native Webex objects. This increases the framework's efficiency and makes it future proof as new attributes are added to the various webex DTOs ");
});

/* On mention with command, using other trigger data, can use lite markdown formatting
ex User enters @botname 'info' phrase, the bot will provide personal details
*/
framework.hears('info', function (bot, trigger) {
  console.log("info command received");
  responded = true;
  //the "trigger" parameter gives you access to data about the user who entered the command
  let personAvatar = trigger.person.avatar;
  let personEmail = trigger.person.emails[0];
  let personDisplayName = trigger.person.displayName;
  let outputString = `Here is your personal information: \n\n\n **Name:** ${personDisplayName}  \n\n\n **Email:** ${personEmail} \n\n\n **Avatar URL:** ${personAvatar}`;
  bot.say("markdown", outputString);
});

/* On mention with bot data 
ex User enters @botname 'space' phrase, the bot will provide details about that particular space
*/
framework.hears('space', function (bot) {
  console.log("space. the final frontier");
  responded = true;
  let roomTitle = bot.room.title;
  let spaceID = bot.room.id;
  let roomType = bot.room.type;

  let outputString = `The title of this space: ${roomTitle} \n\n The roomID of this space: ${spaceID} \n\n The type of this space: ${roomType}`;

  console.log(outputString);
  bot.say("markdown", outputString)
    .catch((e) => console.error(`bot.say failed: ${e.message}`));

});

/* 
   Say hi to every member in the space
   This demonstrates how developers can access the webex
   sdk to call any Webex API.  API Doc: https://webex.github.io/webex-js-sdk/api/
*/
framework.hears("say hi to everyone", function (bot) {
  console.log("say hi to everyone.  Its a party");
  responded = true;
  // Use the webex SDK to get the list of users in this space
  bot.webex.memberships.list({roomId: bot.room.id})
    .then((memberships) => {
      for (const member of memberships.items) {
        if (member.personId === bot.person.id) {
          // Skip myself!
          continue;
        }
        let displayName = (member.personDisplayName) ? member.personDisplayName : member.personEmail;
        bot.say(`Hello ${displayName}`);
      }
    })
    .catch((e) => {
      console.error(`Call to sdk.memberships.get() failed: ${e.messages}`);
      bot.say('Hello everybody!');
    });
});

framework.hears(/break message start/, function(bot, trigger) {
  console.log("user triggered start of periodic messages");
  responded = true;
  let message = trigger.text
  let duration = parseInt(message.match(/\d+/g))
  if (Number.isInteger(duration)) {
    scheduler.removeById(`periodic messageTask to ${trigger.personId}`)
    let messageTask = new Task(
      `periodic message to ${trigger.person.displayName}`,
      () => sendBreakMessage(bot, trigger.personId)
    )
    let messageJob = new SimpleIntervalJob(
      { minutes: duration}, 
      messageTask,
      `periodic messageTask to ${trigger.personId}`)
    scheduler.addSimpleIntervalJob(messageJob)
    bot.say(`Will remind you to take a break every ${duration} minutes`)
  } else {
    bot.say('Invalid argument!')
  }
});

function sendBreakMessage(bot, personId) {
  if (!(personId in data)) {
    data[personId] = 'defaultbreak'
  }
  switch(data[personId]) {
    case 'meditation':
      bot.say('Time for some mediation!')
      break
    case 'screenbreak':
      bot.say('Time to take a screen break!')
      break
    case 'defaultbreak':
      bot.say('Time to take a break!')
      break
  }
}

framework.hears("break message stop", function(bot, trigger) {
  console.log("user triggered stop of periodic messages");
  responded = true;
  scheduler.removeById(`periodic messageTask to ${trigger.personId}`)
  bot.say('Stopping break reminders')
});

// Buttons & Cards data
let cardJSON =
{
  $schema: "http://adaptivecards.io/schemas/adaptive-card.json",
  type: 'AdaptiveCard',
  version: '1.0',
  body:
    [{
      type: 'ColumnSet',
      columns:
        [{
          type: 'Column',
          width: '5',
          items:
            [{
              type: 'Image',
              url: 'Your avatar appears here!',
              size: 'large',
              horizontalAlignment: "Center",
              style: 'person'
            },
            {
              type: 'TextBlock',
              text: 'Your name will be here!',
              size: 'medium',
              horizontalAlignment: "Center",
              weight: 'Bolder'
            },
            {
              type: 'TextBlock',
              text: 'And your email goes here!',
              size: 'small',
              horizontalAlignment: "Center",
              isSubtle: true,
              wrap: false
            }]
        }]
    }]
};

let breakCard = 
{
  "$schema": "http://adaptivecards.io/schemas/adaptive-card.json",
  "type": "AdaptiveCard",
  "version": "1.2",
  "body": [
      {
          "type": "ColumnSet",
          "columns": [
              {
                  "type": "Column",
                  "width": 2,
                  "items": [
                      {
                          "type": "TextBlock",
                          "text": "Quick Check-In",
                          "weight": "bolder",
                          "size": "medium"
                      },
                      {
                          "type": "TextBlock",
                          "isSubtle": true,
                          "wrap": true,
                          "text": "Reflect on your day so far."
                      },
                      {
                          "type": "TextBlock",
                          "wrap": true,
                          "text": "How are you feeling right now?"
                      },
                      {
                          "type": "Input.ChoiceSet",
                          "id": "reminderType",
                          "choices": [
                              {
                                  "title": "Feeling a bit stressed",
                                  "value": "meditation"
                              },
                              {
                                  "title": "Have a bit of a headache",
                                  "value": "screenbreak"
                              },
                              {
                                  "title": "Feeling fine",
                                  "value": "defaultbreak"
                              }
                          ],
                          "placeholder": "Feeling fine",
                          "value": "defaultbreak"
                      }
                  ]
              },
              {
                  "type": "Column",
                  "width": 1,
                  "items": [
                      {
                          "type": "Image",
                          "url": "https://i.postimg.cc/wMJvqNR6/sign-up.jpg"
                      }
                  ]
              }
          ]
      }
  ],
  "actions": [
      {
          "type": "Action.Submit",
          "title": "Submit"
      }
  ]
}

/* On mention with card example
ex User enters @botname 'card me' phrase, the bot will produce a personalized card - https://developer.webex.com/docs/api/guides/cards
*/
framework.hears('card me', function (bot, trigger) {
  console.log("someone asked for a card");
  responded = true;
  let avatar = trigger.person.avatar;

  cardJSON.body[0].columns[0].items[0].url = (avatar) ? avatar : `${config.webhookUrl}/missing-avatar.jpg`;
  cardJSON.body[0].columns[0].items[1].text = trigger.person.displayName;
  cardJSON.body[0].columns[0].items[2].text = trigger.person.emails[0];
  bot.sendCard(cardJSON, 'This is customizable fallback text for clients that do not support buttons & cards');
});

framework.hears('checkin', function (bot) {
  console.log("someone is doing a check-in")
  responded = true;

  bot.sendCard(breakCard, 'This feature is not supported')
})

framework.on('attachmentAction', function (bot, trigger) {
  //console.log(trigger.attachmentAction)
  var reply = ""
  if (Object.keys(trigger.attachmentAction.inputs).length != 0) {
    reminderType = trigger.attachmentAction.inputs.reminderType
    data[trigger.personId] = reminderType
    switch (reminderType) {
      case "meditation":
        reply = "If you're feeling stressed, you can do some meditation to help yourself relax."
        break
      case "screenbreak":
        reply = "You might have been focusing on your screen for too long. Take a screen break every once in a while."
        break
      case "defaultbreak":
        reply = "Good to know you're doing well! However, you should still remember to take a break from time to time."
        break
    }
  } else {
    data[trigger.personId] = 'defaultbreak'
    reply = "Good to know you're doing well! However, you should still remember to take a break from time to time."
  }
  bot.reply(trigger.attachmentAction, reply)
})

framework.hears('datadebug', function (bot, trigger) {
  console.log(data)
  responded = true

  bot.reply(trigger.message, 'data sent to console')
})

/* On mention reply example
ex User enters @botname 'reply' phrase, the bot will post a threaded reply
*/
framework.hears('reply', function (bot, trigger) {
  console.log("someone asked for a reply.  We will give them two.");
  responded = true;
  bot.reply(trigger.message, 
    'This is threaded reply sent using the `bot.reply()` method.',
    'markdown');
  var msg_attach = {
    text: "This is also threaded reply with an attachment sent via bot.reply(): ",
    file: 'https://media2.giphy.com/media/dTJd5ygpxkzWo/giphy-downsized-medium.gif'
  };
  bot.reply(trigger.message, msg_attach);
});

/* On mention with unexpected bot command
   Its a good practice is to gracefully handle unexpected input
*/
framework.hears(/.*/, function (bot, trigger) {
  // This will fire for any input so only respond if we haven't already
  if (!responded) {
    console.log(`catch-all handler fired for user input: ${trigger.text}`);
    bot.say(`Sorry, I don't know how to respond to "${trigger.text}"`)
      .then(() => sendHelp(bot))
      .catch((e) => console.error(`Problem in the unexepected command hander: ${e.message}`));
  }
  responded = false;
});

function sendHelp(bot) {
  bot.say("markdown", 'These are the commands I can respond to:', '\n\n ' +
    '1. **framework**   (learn more about the Webex Bot Framework) \n' +
    '2. **info**  (get your personal details) \n' +
    '3. **space**  (get details about this space) \n' +
    '4. **card me** (a cool card!) \n' +
    '5. **say hi to everyone** (everyone gets a greeting using a call to the Webex SDK) \n' +
    '6. **reply** (have bot reply to your message) \n' +
    '7. **help** (what you are reading now)');
}


//Server config & housekeeping
// Health Check
app.get('/', function (req, res) {
  res.send(`I'm alive.`);
});

app.post('/', webhook(framework));

var server = app.listen(config.port, function () {
  framework.debug('framework listening on port %s', config.port);
});

// gracefully shutdown (ctrl-c)
process.on('SIGINT', function () {
  framework.debug('stoppping...');
  server.close();
  scheduler.stop();
  framework.stop().then(function () {
    process.exit();
  });
});
