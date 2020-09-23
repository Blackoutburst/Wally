//====================================================
//                                                      CONST VAR
//====================================================

const { createCanvas, loadImage } = require('canvas');
const request = require('request');
const Discord = require('discord.js');
const fs = require('fs');
const mkdirp = require('mkdirp');
const client = new Discord.Client();
const Hypixel = require('node-hypixel');
const hypixel = new Hypixel('b9ca2097-103e-42fe-93fa-8808f11ed7e4');
const token = 'NzU1NTU5NDQ0MDM2ODQ1NjAx.X2FDkQ.qeFXhybTvyyjWrLAK3cbWaiuH04';
const serverid = '709521394848890880';

const width = 600;
const height = 400;
var requestSend = 0;

//====================================================
//                                                   BOT COMMANDS
//====================================================

client.on('message', msg => {
    if (msg.author.bot)return;
    if (msg.member === null) return;

    if (msg.content === "!role" && msg.author.id === '160854225943789569')showRole(msg);
    if (msg.content === "!guild" && msg.author.id === '160854225943789569')showGuild(msg);
    if (msg.content.startsWith("!say") && msg.author.id === '160854225943789569')talk(msg);
    if (msg.content.startsWith("!usr") && msg.author.id === '160854225943789569')getUsrRole(msg);

    if (msg.content === "!help")showHelp(msg);
    if (msg.content === "!pack")givePackLink(msg);
    if (msg.content.startsWith("!stats") || msg.content.startsWith("!stat"))showStats(msg);
    if (msg.content.startsWith("!compare"))compare(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!tracked")showTrackedPlayer(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!linked")showLinkedPlayer(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!link") && msg.content !== "!linked" || msg.author.id === '160854225943789569' && msg.content.startsWith("!link") && msg.content !== "!linked")linkPlayer(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!unlink"))unlinkPlayer(msg);
    if(msg.member.permissions.has('ADMINISTRATOR') &&  msg.content === "!ping" || msg.author.id === '160854225943789569' &&  msg.content === "!ping")showPing(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!track") && msg.content !== "!tracked" || msg.author.id === '160854225943789569' && msg.content.startsWith("!track") && msg.content !== "!tracked")startTracking(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') &&  msg.content.startsWith("!stop"))stopTracking(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') && msg.content.startsWith("!settracker"))setTracker(msg);
    if (msg.member.permissions.has('ADMINISTRATOR') && msg.content.startsWith("!showtracker"))showTracker(msg);
});

//====================================================
//                                                   DUMB STUFF
//====================================================

async function getUsrRole(msg){
    var str = await msg.content.split(" ");
    var serv = await client.guilds.get(serverid);
    var disc = await client.guilds.get(serverid).members.get(str[1]);

    if (disc.roles.find(r => r.name === "350+ Club")){
        console.log("350 true");
    } else {
        console.log("350 false");
    }
    if (disc.roles.find(r => r.name === "300+ Club")){
        console.log("300 true");
    } else {
        console.log("300 false");
    }
    if (disc.roles.find(r => r.name === "250+ Club")){
        console.log("250 true");
    } else {
        console.log("250 false");
    }
    if (disc.roles.find(r => r.name === "200+ Club")){
        console.log("200 true");
    } else {
        console.log("200 false");
    }
    if (disc.roles.find(r => r.name === "150+ Club")){
        console.log("150 true");
    } else {
        console.log("150 false");
    }
    if (disc.roles.find(r => r.name === "100+ Club")){
        console.log("100 true");
    } else {
        console.log("100 false");
    }
    if (disc.roles.find(r => r.name === "50+ Club")){
        console.log("50 true");
    } else {
        console.log("50 false");
    }
}

function talk(msg) {
    msg.channel.send(msg.content.replace("!say", ""))
    msg.delete();
}

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
                var serv = client.guilds.get(serverid);
                if (fs.existsSync('linked player/'+uuid[7]+","+discord)) {
                    if (player.stats.Arcade.hitw_record_q >= 350 || player.stats.Arcade.hitw_record_f >= 350){user.addRole(msg.guild.roles.find(r => r.name === "350+ Club"));user.removeRole(serv.roles.find(r => r.name === "300+ Club"));user.removeRole(serv.roles.find(r => r.name === "250+ Club"));user.removeRole(serv.roles.find(r => r.name === "200+ Club"));user.removeRole(serv.roles.find(r => r.name === "150+ Club"));user.removeRole(serv.roles.find(r => r.name === "100+ Club"));user.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                    else if (player.stats.Arcade.hitw_record_q >= 300 || player.stats.Arcade.hitw_record_f >= 300){user.addRole(msg.guild.roles.find(r => r.name === "300+ Club"));user.removeRole(serv.roles.find(r => r.name === "250+ Club"));user.removeRole(serv.roles.find(r => r.name === "200+ Club"));user.removeRole(serv.roles.find(r => r.name === "150+ Club"));user.removeRole(serv.roles.find(r => r.name === "100+ Club"));user.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                    else if (player.stats.Arcade.hitw_record_q >= 250 || player.stats.Arcade.hitw_record_f >= 250){user.addRole(msg.guild.roles.find(r => r.name === "250+ Club"));user.removeRole(serv.roles.find(r => r.name === "200+ Club"));user.removeRole(serv.roles.find(r => r.name === "150+ Club"));user.removeRole(serv.roles.find(r => r.name === "100+ Club"));user.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                    else if (player.stats.Arcade.hitw_record_q >= 200 || player.stats.Arcade.hitw_record_f >= 200){user.addRole(msg.guild.roles.find(r => r.name === "200+ Club"));user.removeRole(serv.roles.find(r => r.name === "150+ Club"));user.removeRole(serv.roles.find(r => r.name === "100+ Club"));user.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                    else if (player.stats.Arcade.hitw_record_q >= 150 || player.stats.Arcade.hitw_record_f >= 150){user.addRole(msg.guild.roles.find(r => r.name === "150+ Club"));user.removeRole(serv.roles.find(r => r.name === "100+ Club"));user.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                    else if (player.stats.Arcade.hitw_record_q >= 100 || player.stats.Arcade.hitw_record_f >= 100){user.addRole(msg.guild.roles.find(r => r.name === "100+ Club"));user.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                    else if (player.stats.Arcade.hitw_record_q >= 50 || player.stats.Arcade.hitw_record_f >= 50){user.addRole(msg.guild.roles.find(r => r.name === "50+ Club"));return;}
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
    var Q = 0;
    var F = 0;

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
                fs.mkdirSync('tracked player/'+uuid[7], function(err) {});
                fs.writeFileSync('tracked player/'+uuid[7]+"/Q", Q);
                fs.writeFileSync('tracked player/'+uuid[7]+"/F", F);
                msg.channel.send("Player tracked successfully!\n```yml\nCurrent "+player.displayname+" stats\nQ: "+Q+"\nF: "+F+"```");
            });
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
                fs.mkdirSync('tracked player/'+uuid[7], function(err) {});
                fs.writeFileSync('tracked player/'+uuid[7]+"/Q", Q);
                fs.writeFileSync('tracked player/'+uuid[7]+"/F", F);
                msg.channel.send("Player tracked successfully!\n```yml\nCurrent "+player.displayname+" stats\nQ: "+Q+"\nF: "+F+"```");
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
        msg.reply('```yml\n\n!help: show this message\n\n!pack: give link to the Hole in the Wall ressources pack made by Hammy in the Wall\n\n!ping: show current lag and api lag\n\n!stats [player]: show a player HitW stats\n\n!compare [player] [player]: compre two players HitW stats\n\n!tracked: list tracked player\n\n!linked: list linked player\n\n!track [player]: track a player PB\n\n!stop [player]: stop tracking a player PB\n\n!settracker: select the current channel as the channel where every new player tracked pb is sended\n\n!showtracker: show the current track channel\n\n!link [@user IGN]: link the player discord account with his in game one\n\n!unlink [@user IGN]: unlink a player from his in game account```');
    } else {
        msg.reply('```yml\n\n!help: show this message\n\n!pack: give link to the Hole in the Wall ressources pack made by Hammy in the Wall\n\n!stats [player]: show a player HitW stats\n\n!compare [player] [player]: compre two players HitW stats```');
    }
}

//=======================  !compare  ========================

async function compare(msg) {
    var str = msg.content.split(" ");
    var player = null;
    var player2 = null;
    var value = null;
    var value2 = null;
    var total = 0;
    var total2 = 0;
    var Q = 0;
    var F = 0;
    var W = 0;
    var R = 0;
    var Q2 = 0;
    var F2 = 0;
    var W2 = 0;
    var R2 = 0;
    const canvas = createCanvas(width, height);
    const context = canvas.getContext('2d');


    if (str[1] == null) {
        msg.reply('Improper command usage please try **!compare player player**');
        return;
    }
    player = str[1];
    value = await hypixel.getPlayerByUsername(player);
    requestSend++;
    console.log("Request count : "+requestSend);
    player2 = str[2];
    value2 = await hypixel.getPlayerByUsername(player2);
    requestSend++;
    console.log("Request count : "+requestSend);

    if (value == null) {
        msg.reply(str[1]+' not found!');
        return;
    }
    if (value2 == null) {
        msg.reply(str[2]+' not found!');
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

    W2 = value2.stats.Arcade.wins_hole_in_the_wall;
    if (W2 == null) W2 = 0;
    R2 = value2.stats.Arcade.rounds_hole_in_the_wall;
    if (R2 == null) R2 = 0;
    Q2 = value2.stats.Arcade.hitw_record_q;
    if (Q2 == null) Q2 = 0;
    F2 = value2.stats.Arcade.hitw_record_f;
    if (F2 == null) F2 = 0;

    total2 = Number(Q2)+Number(F2);

    loadImage('res/background.png').then(image => {
        context.drawImage(image, 0, 0, 600, 400);
        loadImage('res/win.png').then(win => {
            context.drawImage(win, 10, 105, 24, 24);
            context.drawImage(win, 566, 105, 24, 24);
            loadImage('res/wall.png').then(wall => {
                context.drawImage(wall, 10, 155, 24, 24);
                context.drawImage(wall, 566, 155, 24, 24);
                loadImage('res/q.png').then(q => {
                    context.drawImage(q, 10, 205, 24, 24);
                    context.drawImage(q, 566, 205, 24, 24);
                    loadImage('res/f.png').then(f => {
                        context.drawImage(f, 10, 255, 24, 24);
                        context.drawImage(f, 566, 255, 24, 24);
                        loadImage('res/total.png').then(tot => {
                            context.drawImage(tot, 10, 305, 24, 24);
                            context.drawImage(tot, 566, 305, 24, 24);
                            const text = value.displayname;

                            context.font = 'regular 24pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(text, 10, 40)

                            const text2 = value2.displayname;

                            context.font = 'regular 24pt '
                            context.textAlign = 'right'
                            context.fillStyle = '#fff'
                            context.fillText(text2, 590, 40)

                            const wi = 'Wins: '+String(W).replace(/(.)(?=(\d{3})+$)/g,'$1,')+" ("+String(Number(W)-Number(W2)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+")";

                            context.font = 'regular 14pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(wi, 50, 125)

                            const ro = 'Walls: '+String(R).replace(/(.)(?=(\d{3})+$)/g,'$1,')+" ("+String(Number(R)-Number(R2)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+")";

                            context.font = 'regular 14pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(ro, 50, 175)

                            const qu = 'Qualification: '+String(Q).replace(/(.)(?=(\d{3})+$)/g,'$1,')+" ("+String(Number(Q)-Number(Q2)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+")";

                            context.font = 'regular 14pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(qu, 50, 225)

                            const fi = 'Finals: '+String(F).replace(/(.)(?=(\d{3})+$)/g,'$1,')+" ("+String(Number(F)-Number(F2)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+")";

                            context.font = 'regular 14pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(fi, 50, 275)

                            const to = 'Q/F Total: '+String(total).replace(/(.)(?=(\d{3})+$)/g,'$1,')+" ("+String(Number(total)-Number(total2)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+")";

                            context.font = 'regular 14pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(to, 50, 325)


                            const wi2 = "("+String(Number(W2)-Number(W)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+") "+String(W2).replace(/(.)(?=(\d{3})+$)/g,'$1,')+' :Wins';

                            context.font = 'regular 14pt '
                            context.textAlign = 'right'
                            context.fillStyle = '#fff'
                            context.fillText(wi2, 550, 125)

                            const ro2 = "("+String(Number(R2)-Number(R)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+") "+String(R2).replace(/(.)(?=(\d{3})+$)/g,'$1,')+' :Walls';

                            context.font = 'regular 14pt '
                            context.textAlign = 'right'
                            context.fillStyle = '#fff'
                            context.fillText(ro2, 550, 175)

                            const qu2 = "("+String(Number(Q2)-Number(Q)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+") "+String(Q2).replace(/(.)(?=(\d{3})+$)/g,'$1,')+' :Qualification';

                            context.font = 'regular 14pt '
                            context.textAlign = 'right'
                            context.fillStyle = '#fff'
                            context.fillText(qu2, 550, 225)

                            const fi2 = "("+String(Number(F2)-Number(F)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+") "+String(F2).replace(/(.)(?=(\d{3})+$)/g,'$1,')+' :Finals';

                            context.font = 'regular 14pt '
                            context.textAlign = 'right'
                            context.fillStyle = '#fff'
                            context.fillText(fi2, 550, 275)

                            const to2 = "("+String(Number(total2)-Number(total)).replace(/(.)(?=(\d{3})+$)/g,'$1,')+") "+String(total2).replace(/(.)(?=(\d{3})+$)/g,'$1,')+' :Q/F Total';

                            context.font = 'regular 14pt '
                            context.textAlign = 'right'
                            context.fillStyle = '#fff'
                            context.fillText(to2, 550, 325)

                            const buffer = canvas.toBuffer('image/png');
                            fs.writeFileSync('./vs.png', buffer);
                            msg.channel.send({
                                files: ['./vs.png']
                            });
                        });
                    });
                });
            });
        });
    });
}

//=======================  !stats  ========================

async function showStats(msg) {
    const canvas = createCanvas(width, height);
    const context = canvas.getContext('2d');
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

    loadImage('res/background.png').then(image => {
        context.drawImage(image, 0, 0, 600, 400);
        loadImage('res/win.png').then(win => {
            context.drawImage(win, 100, 105, 24, 24)
            loadImage('res/wall.png').then(wall => {
                context.drawImage(wall, 100, 155, 24, 24)
                loadImage('res/q.png').then(q => {
                    context.drawImage(q, 100, 205, 24, 24)
                    loadImage('res/f.png').then(f => {
                        context.drawImage(f, 100, 255, 24, 24)
                        loadImage('res/total.png').then(tot => {
                            context.drawImage(tot, 100, 305, 24, 24)
                            const text = value.displayname+' stats';

                            context.font = 'regular 24pt '
                            context.textAlign = 'center'
                            context.fillStyle = '#fff'
                            context.fillText(text, 300, 40)

                            const wi = 'Wins: '+String(W).replace(/(.)(?=(\d{3})+$)/g,'$1,');

                            context.font = 'regular 16pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(wi, 150, 125)

                            const ro = 'Walls Cleared: '+String(R).replace(/(.)(?=(\d{3})+$)/g,'$1,');

                            context.font = 'regular 16pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(ro, 150, 175)

                            const qu = 'Best Qualification Score: '+String(Q).replace(/(.)(?=(\d{3})+$)/g,'$1,');

                            context.font = 'regular 16pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(qu, 150, 225)

                            const fi = 'Best Finals Score: '+String(F).replace(/(.)(?=(\d{3})+$)/g,'$1,');

                            context.font = 'regular 16pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(fi, 150, 275)

                            const to = 'Q/F Total: '+String(total).replace(/(.)(?=(\d{3})+$)/g,'$1,');

                            context.font = 'regular 16pt '
                            context.textAlign = 'left'
                            context.fillStyle = '#fff'
                            context.fillText(to, 150, 325)

                            const buffer = canvas.toBuffer('image/png')
                            fs.writeFileSync('./image.png', buffer)
                            msg.channel.send({
                                files: ['./image.png']
                            });
                        });
                    });
                });
            });
        });
    });
}

//====================================================
//                                                 TRACKING SYSTEM
//====================================================

async function tracker() {
    var channel = ""
    var Q = 0;
    var F = 0;
    var interval = 750;

    console.log("Tracking player pb current request this minute : "+requestSend);
    if (!fs.existsSync("tracker"))return;
    fs.readdir("tracked player", (err, files) => {
        files.forEach(function (file, index) {
            setTimeout(function () {
                fs.readFile('tracker', 'utf8', function (err,data) {
                    if (err) {
                        return console.log(err);
                    }
                    if(requestSend >= 119) {
                        console.log("Tracker failled to get everyone request limit reached");
                        return;
                    }
                    hypixel.getPlayer(file).then(value => {
                        requestSend++;
                        fs.readFile('tracked player/'+file+"/Q", 'utf8', function (err2,score) {
                            if (err2) {
                                return console.log(err2);
                            }
                            Q = value.stats.Arcade.hitw_record_q;
                            if (Q == null) Q = 0;
                            if (Number(Q) > Number(score)) {
                                client.channels.get(data.replace('<#', "").slice(0, -1)).send("**"+value.displayname+"** scored a new **qualifiers** personal best of **"+Q+"** !");
                                fs.writeFileSync('tracked player/'+file+"/Q", Q);
                                fs.readdir("linked player", (err, F1) => {
                                    F1.forEach(F2 => {
                                        if (String(F2).startsWith(file)) {
                                            var usr = F2.split(",");
                                            var serv = client.guilds.get(serverid);
                                            var disc = client.guilds.get(serverid).members.get(usr[1]);
                                            if (value.stats.Arcade.hitw_record_q >= 350){disc.addRole(serv.roles.find(r => r.name === "350+ Club"));disc.removeRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 300 && !disc.roles.find(r => r.name === "350+ Club")){disc.addRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 250 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club")){disc.addRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 200 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club")){disc.addRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 150 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club") && !disc.roles.find(r => r.name === "200+ Club")){disc.addRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 100 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club") && !disc.roles.find(r => r.name === "200+ Club") && !disc.roles.find(r => r.name === "150+ Club")){disc.addRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_q >= 50 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club") && !disc.roles.find(r => r.name === "200+ Club") && !disc.roles.find(r => r.name === "150+ Club") && !disc.roles.find(r => r.name === "100+ Club")){disc.addRole(serv.roles.find(r => r.name === "50+ Club"));return;}
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
                                client.channels.get(data.replace('<#', "").slice(0, -1)).send("**"+value.displayname+"**  scored a new **finals** personal best of **"+F+"** !");
                                fs.writeFileSync('tracked player/'+file+"/F", F);
                                fs.readdir("linked player", (err, F1) => {
                                    F1.forEach(F2 => {
                                        if (String(F2).startsWith(file)) {
                                            var usr = F2.split(",");
                                            var serv = client.guilds.get(serverid);
                                            var disc = client.guilds.get(serverid).members.get(usr[1]);
                                            if (value.stats.Arcade.hitw_record_f >= 350){disc.addRole(serv.roles.find(r => r.name === "350+ Club"));disc.removeRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 300 && !disc.roles.find(r => r.name === "350+ Club")){disc.addRole(serv.roles.find(r => r.name === "300+ Club"));disc.removeRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 250 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club")){disc.addRole(serv.roles.find(r => r.name === "250+ Club"));disc.removeRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 200 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club")){disc.addRole(serv.roles.find(r => r.name === "200+ Club"));disc.removeRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 150 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club") && !disc.roles.find(r => r.name === "200+ Club")){disc.addRole(serv.roles.find(r => r.name === "150+ Club"));disc.removeRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 100 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club") && !disc.roles.find(r => r.name === "200+ Club") && !disc.roles.find(r => r.name === "150+ Club")){disc.addRole(serv.roles.find(r => r.name === "100+ Club"));disc.removeRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                            if (value.stats.Arcade.hitw_record_f >= 50 && !disc.roles.find(r => r.name === "350+ Club") && !disc.roles.find(r => r.name === "300+ Club") && !disc.roles.find(r => r.name === "250+ Club") && !disc.roles.find(r => r.name === "200+ Club") && !disc.roles.find(r => r.name === "150+ Club") && !disc.roles.find(r => r.name === "100+ Club")){disc.addRole(serv.roles.find(r => r.name === "50+ Club"));return;}
                                        }
                                    });
                                });
                            }
                        });
                    });
                });
            }, index * interval);
        });
    });
}

setInterval(tracker, 120000);

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
