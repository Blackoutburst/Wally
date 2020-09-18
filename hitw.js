//====================================================
//                                                      CONST VAR
//====================================================

const Discord = require('discord.js');
const fs = require('fs');
const mkdirp = require('mkdirp');
const client = new Discord.Client();
const Hypixel = require('node-hypixel');
const hypixel = new Hypixel('Yes but no i this is my api code not your'');
const token = 'Same for here go get your own bot token';

//====================================================
//                                                   BOT COMMANDS
//====================================================

client.on('message', msg => {
    //Ignore bot message
    if (msg.author.bot)return;

    //Commands
    if (msg.content === "!help")showHelp(msg);
    if (msg.content === "!pack")givePackLink(msg);
    if (msg.content.startsWith("!stats") || msg.content.startsWith("!stat"))showStats(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!ping")showPing(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!list")showTrackedPlayer(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!track"))startTracking(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!stop"))stopTracking(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') && msg.content.startsWith("!settracker"))setTracker(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') && msg.content.startsWith("!showtracker"))showTracker(msg);
});

//====================================================
//                                           COMMANDS FUNCTION
//====================================================

//======================  !stop =========================

function stopTracking(msg) {
    var str = msg.content.split(" ");

    if (str[1] == null) {
        msg.reply('Bad command usage please try **!stop player**');
        return;
    }
    if (!fs.existsSync('tracked player/'+str[1])) {
        msg.reply('User not tracked!');
        return;
    }
    fs.rmdir('tracked player/'+str[1], { recursive: true }, (err) => {
    if (err) {
        throw err;
    }
        msg.channel.send("Stopped tracking "+str[1]+" personnal best!");
    });
}

//======================  !track =========================

async function startTracking(msg) {
    var player = null;
    var str = msg.content.split(" ");
    var Q = 0;
    var F = 0;

    if (!fs.existsSync("tracker") {
        msg.reply("Before tracking any player please set a tracking channel with **!settracker** !");
        return;
    }
    if (str[1] == null) {
        msg.reply('Bad command usage please try **!track player**');
        return;
    }
    player = await hypixel.getPlayerByUsername(str[1]);
    if (player == null) {
        msg.reply("Player not found");
        return;
    }
    if (fs.existsSync('tracked player/'+str[1])) {
        msg.reply('User already tracked!');
        return;
    }
    Q = player.stats.Arcade.hitw_record_q;
    if (Q == null) Q = 0;
    F = player.stats.Arcade.hitw_record_f;
    if (F == null) F = 0;
    fs.mkdir('tracked player/'+str[1], function(err) {});
    fs.writeFileSync('tracked player/'+str[1]+"/Q", Q);
    fs.writeFileSync('tracked player/'+str[1]+"/F", F);
    msg.channel.send("Player tracked successfully!\n```yml\nCurrent "+player.displayname+" stats\nQ: "+Q+"\nF: "+F+"```");
}

//=======================  !list  =========================

function showTrackedPlayer(msg) {
    var playerlist = "```yml\nPlayer :\n"

    fs.readdir("tracked player", (err, files) => {
        files.forEach(file => {
            playerlist += file+' \n';
        });
        msg.channel.send(playerlist+"```");
    });
}

//=====================  !settracker  =======================

function setTracker(msg) {
    fs.writeFileSync('tracker', msg.channel);
    msg.reply("Tracker set in channel "+msg.channel)
}

//=====================  !showtracker  =====================

function showTracker(msg) {
    fs.readFile('tracker', 'utf8', function (err,data) {
    if (err) {
        return console.log(err);
    }
        msg.reply("Tracker currently set in "+data)
    });
}

//=======================  !ping  ========================

async function showPing(msg) {
    const m = await msg.channel.send("...");
    m.edit(`Ping : **${m.createdTimestamp - msg.createdTimestamp}**ms. API : **${Math.round(client.ping)}**ms`);
}

//=======================  !pack  ========================

function givePackLink(msg) {
    msg.reply('You can download the ressources pack here\nhttps://cdn.discordapp.com/attachments/739642373415633017/739642810713505872/Hammys_HitW_Pack_v0.3_Beta.zip\n*All credits goes to Hammy in the Wall*');
}

//=======================  !help  ========================

function showHelp(msg) {
    if (msg.member.permissions.has('ADMINISTRATOR')) {
        msg.reply('```yml\n!help: show this message\n!pack: give link to the Hole in the Wall ressources pack made by Hammy in the Wall\n!ping: show current lag and api lag\n!stats player: show a player HitW stats\n!list: list tracked player\n!track player: track a player PB\n!stop player: stop tracking a player PB\n!settracker: select the current channel as the channel where every new player tracked pb is sended\n!showtracker: show the current track channel```');
    } else {
        msg.reply('```yml\n!help: show this message\n!pack: give link to the Hole in the Wall ressources pack made by Hammy in the Wall\n!stats player: show a player HitW stats\n```');
    }
}

//=======================  !stats  ========================

async function showStats(msg) {
    var str = msg.content.split(" ");
    var player = null;
    var value = null;
    var total = 0;

    if (str[1] == null) {
        msg.reply('Bad command usage please try **!stats player**');
        return;
    }
    player = str[1];
    value = await hypixel.getPlayerByUsername(player);
    if (value == null) {
        msg.reply('Player not found!');
        return;
    }
    total = Number(value.stats.Arcade.hitw_record_q)+Number(value.stats.Arcade.hitw_record_f);
    msg.channel.send("```yml\nHitW Stats: "+value.displayname+"\n\nWins: "+String(value.stats.Arcade.wins_hole_in_the_wall).replace(/(.)(?=(\d{3})+$)/g,'$1,')+"\nRounds Played: "+String(value.stats.Arcade.rounds_hole_in_the_wall).replace(/(.)(?=(\d{3})+$)/g,'$1,')+"\n\nBest Qualification Score: "+value.stats.Arcade.hitw_record_q+"\nBest Finals Score: "+value.stats.Arcade.hitw_record_f+"\n\nQ/F Total: "+total+"```");
}

//====================================================
//                                                 TRACKING SYSTEM
//====================================================

async function tracker() {
    var channel = ""
    var Q = 0;
    var F = 0;

    fs.readdir("tracked player", (err, files) => {
        files.forEach(file => {
            fs.readFile('tracker', 'utf8', function (err,data) {
                if (err) {
                    return console.log(err);
                }
                hypixel.getPlayerByUsername(file).then(value => {
                    fs.readFile('tracked player/'+file+"/Q", 'utf8', function (err2,score) {
                        if (err2) {
                            return console.log(err2);
                        }
                        Q = value.stats.Arcade.hitw_record_q;
                        if (Q == null) Q = 0;
                        if (Number(Q) > Number(score)) {
                            client.channels.get(data.replace('<#', "").slice(0, -1)).send("**"+value.displayname+"** make a new **qualification** personnal record of **"+Q+"** !");
                            fs.writeFileSync('tracked player/'+file+"/Q", Q);
                        }
                    });
                    fs.readFile('tracked player/'+file+"/F", 'utf8', function (err2,score) {
                        if (err2) {
                            return console.log(err2);
                        }
                        F = value.stats.Arcade.hitw_record_f;
                        if (F == null) F = 0;
                        if (Number(F) > Number(score)) {
                            client.channels.get(data.replace('<#', "").slice(0, -1)).send("**"+value.displayname+"** make a new **final** personnal record of **"+F+"** !");
                            fs.writeFileSync('tracked player/'+file+"/F", F);
                        }
                    });
                });
            });
        });
    });
}

//Check new PB every minute (probably need modification later)
setInterval(tracker, 10000);

//====================================================
//                                                       BOT LOGIN
//====================================================

client.on('ready', () =>{client.user.setActivity('!help');})
client.login(token);











// i'm a real asshole in javascript so please do not complain about my awesome code ty
