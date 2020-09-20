//====================================================
//                                                      CONST VAR
//====================================================

const request = require('request');
const Discord = require('discord.js');
const fs = require('fs');
const mkdirp = require('mkdirp');
const client = new Discord.Client();
const Hypixel = require('node-hypixel');
const hypixel = new Hypixel('hypixel api key');
const token = 'discord bot token';
const serverid = 'server id';

var requestSend = 0;

//====================================================
//                                                   BOT COMMANDS
//====================================================

client.on('message', msg => {
    if (msg.author.bot)return;

    if (msg.content === "!role" && msg.author.id === 'bot owner id')showRole(msg);
    if (msg.content === "!guild" && msg.author.id === 'bot owner id')showGuild(msg);

    if (msg.content === "!help")showHelp(msg);
    if (msg.content === "!pack")givePackLink(msg);
    if (msg.content.startsWith("!stats") || msg.content.startsWith("!stat"))showStats(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!tracked")showTrackedPlayer(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!linked")showLinkedPlayer(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!link") && msg.content !== "!linked")linkPlayer(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!unlink"))unlinkPlayer(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!ping" || msg.author.id === 'bot owner id' &&  msg.content === "!ping")showPing(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!track") && msg.content !== "!tracked")startTracking(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!stop"))stopTracking(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') && msg.content.startsWith("!settracker"))setTracker(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') && msg.content.startsWith("!showtracker"))showTracker(msg);
});

//====================================================
//                                           AUTO SET ROLE FUNCTION
//====================================================

client.on('guildMemberAdd', (guildMember) => {
   guildMember.addRole(guildMember.guild.roles.find(role => role.name === "Members"));
});

async function autoSetRole(msg, discord, ign) {
    var uuid = "";
    var user = client.guilds.get(serverid).members.get(discord);
    if(requestSend >= 119){
        msg.reply("Ammount of request per minute reach please try again in one minute");
        return;
    }
    request('https://api.mojang.com/users/profiles/minecraft/'+ign, function (error, response, body) {
        if (body == "")msg.reply("Player not found");
        if (!error && response.statusCode == 200) {
            uuid = body.split("\"");
            hypixel.getPlayer(uuid[7]).then(player => {
                requestSend++;
                console.log("Request count : "+requestSend);
                if (fs.existsSync('linked player/'+uuid[7]+","+discord)) {
                    if (player.stats.Arcade.hitw_record_q >= 350 || player.stats.Arcade.hitw_record_f >= 350){user.addRole(msg.guild.roles.find(r => r.name === "350+ Club"));return;}
                    if (player.stats.Arcade.hitw_record_q >= 300 || player.stats.Arcade.hitw_record_f >= 300){user.addRole(msg.guild.roles.find(r => r.name === "300+ Club"));return;}
                    if (player.stats.Arcade.hitw_record_q >= 250 || player.stats.Arcade.hitw_record_f >= 250){user.addRole(msg.guild.roles.find(r => r.name === "250+ Club"));return;}
                    if (player.stats.Arcade.hitw_record_q >= 200 || player.stats.Arcade.hitw_record_f >= 200){user.addRole(msg.guild.roles.find(r => r.name === "200+ Club"));return;}
                    if (player.stats.Arcade.hitw_record_q >= 150 || player.stats.Arcade.hitw_record_f >= 150){user.addRole(msg.guild.roles.find(r => r.name === "150+ Club"));return;}
                    if (player.stats.Arcade.hitw_record_q >= 100 || player.stats.Arcade.hitw_record_f >= 100){user.addRole(msg.guild.roles.find(r => r.name === "100+ Club"));return;}
                    if (player.stats.Arcade.hitw_record_q >= 50 || player.stats.Arcade.hitw_record_f >= 50){user.addRole(msg.guild.roles.find(r => r.name === "50+ Club"));return;}
                }
            });
        }
    });
}

//====================================================
//                                           COMMANDS FUNCTION
//====================================================

//========================  !guild ========================

function showGuild(msg) {
    console.log(msg.guild.id);
}

//========================  !role ========================

function showRole(msg) {
    msg.guild.roles.forEach(role => console.log(role.name));
}

//=======================  !unlink =======================

async function unlinkPlayer(msg) {
    var str = msg.content.split(" ");
    var discord = "";
    var discordFolder = "";
    var ign = null;
    var uuid = "";

    if (str[1] == null || str[2] == null) {
        msg.reply('Improper command usage **!unlink @user IGN**');
        return;
    }
    discord = String(msg.mentions.users.first());
    discordFolder = await discord.replace('<@', "").slice(0, -1);
    ign = str[2];
    request('https://api.mojang.com/users/profiles/minecraft/'+str[2], function (error, response, body) {
        if (body == "")msg.reply("Player not found");
        if (!error && response.statusCode == 200) {
            uuid = body.split("\"");
            if (!fs.existsSync('linked player/'+uuid[7]+","+discordFolder)) {
                msg.reply('User not linked!');
                return;
            }
            fs.rmdirSync('linked player/'+uuid[7]+","+discordFolder), { recursive: true }, (err) => {
                if (err) {
                    throw err;
                }
            }
            msg.channel.send("Successfully unlinked "+discord+" from "+uuid[3]);
        }
    });
}

//========================  !link ========================

async function linkPlayer(msg) {
    var str = msg.content.split(" ");
    var discord = "";
    var ign = null;
    var uuid = "";

    if (str[1] == null || str[2] == null) {
        msg.reply('Improper command usage **!link @user IGN**');
        return;
    }
    //discord = String(msg.mentions.users.first());
    discord = str[1]
    ign = str[2];

    request('https://api.mojang.com/users/profiles/minecraft/'+ign, function (error, response, body) {
        if (body == "")msg.reply("Player not found");
        if (!error && response.statusCode == 200) {
            uuid = body.split("\"");
            if (fs.existsSync('linked player/'+uuid[7]+","+discord)) {
                msg.reply('User already linked!');
                return;
            }
            fs.mkdir('linked player/'+uuid[7]+","+discord, function(err) {});
            msg.channel.send("<@"+discord+"> successfully linked to "+uuid[3]);
            autoSetRole(msg, discord, ign);
        }
    });
}

//========================  !stop ========================

async function stopTracking(msg) {
    var str = msg.content.split(" ");

    if (str[1] == null) {
        msg.reply('Improper command usage please try **!stop player**');
        return;
    }
    request('https://api.mojang.com/users/profiles/minecraft/'+str[1], function (error, response, body) {
        if (!error && response.statusCode == 200) {
            uuid = body.split("\"");
            if (!fs.existsSync('tracked player/'+uuid[7])) {
                msg.reply('User not tracked!');
                return;
            }
            fs.rmdir('tracked player/'+uuid[7], { recursive: true }, (err) => {
            if (err) {
                throw err;
            }
                msg.channel.send("Stopped tracking "+uuid[3]+"'s personnal best!");
            });
        }
    });
}

//======================  !track =========================

async function startTracking(msg) {
    var str = msg.content.split(" ");
    var Q = 0;
    var F = 0;
    var uuid = "";

    if(requestSend >= 119){
        msg.reply("Ammount of request per minute reach please try again in one minute");
        return;
    }
    if (!fs.existsSync("tracker")) {
        msg.reply("Before tracking any player please set a tracking channel with **!settracker** !");
        return;
    }
    if (str[1] == null) {
        msg.reply('Improper command usage please try **!track player**');
        return;
    }

    request('https://api.mojang.com/users/profiles/minecraft/'+str[1], function (error, response, body) {
        if (body == "")msg.reply("Player not found");
        if (!error && response.statusCode == 200) {
            uuid = body.split("\"");
            hypixel.getPlayer(uuid[7]).then(player => {
                requestSend++;
                console.log("Request count : "+requestSend);
                if (player == null || typeof player.stats.Arcade === 'undefined') {
                    msg.reply("Player not found");
                    return;
                }
                if (fs.existsSync('tracked player/'+uuid[7])) {
                    msg.reply('User already tracked!');
                    return;
                }
                Q = player.stats.Arcade.hitw_record_q;
                if (Q == null) Q = 0;
                F = player.stats.Arcade.hitw_record_f;
                if (F == null) F = 0;
                fs.mkdir('tracked player/'+uuid[7], function(err) {});
                fs.writeFileSync('tracked player/'+uuid[7]+"/Q", Q);
                fs.writeFileSync('tracked player/'+uuid[7]+"/F", F);
                msg.channel.send("Player tracked successfully!\n```yml\nCurrent "+player.displayname+" stats\nQ: "+Q+"\nF: "+F+"```");
                return;
            });
        }
    });
}

//=======================  !linked  =======================

async function showLinkedPlayer(msg)  {
    var playerlist = "```yml\nName : IGN\n"
    const m = await msg.channel.send("...");

    fs.readdir("linked player", (err, files) => {
        files.forEach(file => {
                var str = file.split(",");
                var disc = client.guilds.get(serverid).members.get(str[1]);
                request('https://sessionserver.mojang.com/session/minecraft/profile/'+str[0], function (error, response, body) {
                    if (body == "")msg.reply("Player not found");
                    if (!error && response.statusCode == 200) {
                        var name = body.split("\"");
                        playerlist += disc.displayName+" : "+name[7]+'\n';
                        m.edit(playerlist+"```");
                }
            });
        });
    });
}

//======================  !tracked  =======================

async function showTrackedPlayer(msg) {
    var playerlist = "```yml\nPlayer : uuid\n"
    const m = await msg.channel.send("...");

    fs.readdir("tracked player", (err, files) => {
        files.forEach(file => {
            request('https://sessionserver.mojang.com/session/minecraft/profile/'+file, function (error, response, body) {
                if (body == "")msg.reply("Player not found");
                if (!error && response.statusCode == 200) {
                    var name = body.split("\"");
                    playerlist += name[7]+" : "+file+'\n';
                    m.edit(playerlist+"```");
                }
            });
        });
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
        msg.reply('```yml\n\n!help: show this message\n\n!pack: give link to the Hole in the Wall ressources pack made by Hammy in the Wall\n\n!ping: show current lag and api lag\n\n!stats [player]: show a player HitW stats\n\n!tracked: list tracked player\n\n!linked: list linked player\n\n!track [player]: track a player PB\n\n!stop [player]: stop tracking a player PB\n\n!settracker: select the current channel as the channel where every new player tracked pb is sended\n\n!showtracker: show the current track channel\n\n!link [@user IGN]: link the player discord account with his in game one\n\n!unlink [@user IGN]: unlink a player from his in game account```');
    } else {
        msg.reply('```yml\n\n!help: show this message\n\n!pack: give link to the Hole in the Wall ressources pack made by Hammy in the Wall\n\n!stats [player]: show a player HitW stats\n\n```');
    }
}

//=======================  !stats  ========================

async function showStats(msg) {
    var str = msg.content.split(" ");
    var player = null;
    var value = null;
    var total = 0;
    var Q = 0;
    var F = 0;
    var W = 0;
    var R = 0;

    if(requestSend >= 119){
        msg.reply("Ammount of request per minute reach please try again in one minute");
        return;
    }

    if (str[1] == null) {
        msg.reply('Improper command usage please try **!stats player**');
        return;
    }
    player = str[1];
    value = await hypixel.getPlayerByUsername(player);
    requestSend++;
    console.log("Request count : "+requestSend);
    if (value == null) {
        msg.reply('Player not found!');
        return;
    }
    W = value.stats.Arcade.wins_hole_in_the_wall;
    if (W == null) W = 0;
    R = value.stats.Arcade.rounds_hole_in_the_wall;
    if (R == null) R = 0;
    Q = value.stats.Arcade.hitw_record_q;
    if (Q == null) Q = 0;
    F = value.stats.Arcade.hitw_record_f;
    if (F == null) F = 0;

    total = Number(Q)+Number(F);
    msg.channel.send("```yml\nHitW Stats: "+value.displayname+"\n\nWins: "+String(W).replace(/(.)(?=(\d{3})+$)/g,'$1,')+"\nRounds Played: "+String(R).replace(/(.)(?=(\d{3})+$)/g,'$1,')+"\n\nBest Qualification Score: "+Q+"\nBest Finals Score: "+F+"\n\nQ/F Total: "+total+"```");
}

//====================================================
//                                                 TRACKING SYSTEM
//====================================================

async function tracker() {
    var channel = ""
    var Q = 0;
    var F = 0;

    console.log("Tracking player pb current request this minute : "+requestSend);
    if (!fs.existsSync("tracker"))return;
    fs.readdir("tracked player", (err, files) => {
        files.forEach(file => {
            fs.readFile('tracker', 'utf8', function (err,data) {
                if (err) {
                    return console.log(err);
                }
                sleep(500).then(k => {
                    if(requestSend >= 119) {
                        console.log("Tracker failled to get everyone request limit reached");
                        return;
                    }
                    hypixel.getPlayer(file).then(value => {
                        requestSend++;
                        console.log("Request count : "+requestSend);
                        fs.readFile('tracked player/'+file+"/Q", 'utf8', function (err2,score) {
                            if (err2) {
                                return console.log(err2);
                            }
                            Q = value.stats.Arcade.hitw_record_q;
                            if (Q == null) Q = 0;
                            if (Number(Q) > Number(score)) {
                                client.channels.get(data.replace('<#', "").slice(0, -1)).send("**"+value.displayname+"** made a new **qualification** personnal record of **"+Q+"** !");
                                fs.writeFileSync('tracked player/'+file+"/Q", Q);
                                fs.readdir("linked player", (err, F1) => {
                                    F1.forEach(F2 => {
                                        if (String(F2).startsWith(file)) {
                                            var usr = F2.split(",");
                                            var serv = client.guilds.get(serverid);
                                            var disc = client.guilds.get(serverid).members.get(usr[1]);
                                            if (value.stats.Arcade.hitw_record_q >= 350){disc.addRole(serv.roles.find(r => r.name === "350+ Club"));disc.removeRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 300){disc.addRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 250){disc.addRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 200){disc.addRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 150){disc.addRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 100){disc.addRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 50){disc.addRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                        }
                                    });
                                });
                            }
                        });
                        fs.readFile('tracked player/'+file+"/F", 'utf8', function (err2,score) {
                            if (err2) {
                                return console.log(err2);
                            }
                            F = value.stats.Arcade.hitw_record_f;
                            if (F == null) F = 0;
                            if (Number(F) > Number(score)) {
                                client.channels.get(data.replace('<#', "").slice(0, -1)).send("**"+value.displayname+"** made a new **final** personnal record of **"+F+"** !");
                                fs.writeFileSync('tracked player/'+file+"/F", F);
                                fs.readdir("linked player", (err, F1) => {
                                    F1.forEach(F2 => {
                                        if (String(F2).startsWith(file)) {
                                            var usr = F2.split(",");
                                            var serv = client.guilds.get(serverid);
                                            var disc = client.guilds.get(serverid).members.get(usr[1]);
                                            if (value.stats.Arcade.hitw_record_f >= 350){disc.addRole(serv.roles.find(r => r.name === "350+ Club"));disc.removeRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 300){disc.addRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 250){disc.addRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 200){disc.addRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 150){disc.addRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 100){disc.addRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 50){disc.addRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                        }
                                    });
                                });
                            }
                        });
                    });
                });
            });
        });
    });
}

setInterval(tracker, 100000);

function sleep(ms) {
  return new Promise((resolve) => {
    setTimeout(resolve, ms);
  });
}

//====================================================
//                                                 REQUEST COUNTER
//====================================================

function resetRequest() {
    console.log("Request send during the previous minute : "+requestSend)
    requestSend = 0;
}

setInterval(resetRequest, 60000);

//====================================================
//                                                       BOT LOGIN
//====================================================

client.on('ready', () =>{client.user.setActivity('!help');})
client.login(token);











// i'm a real asshole in javascript so please do not complain about my awesome code ty
