const { registerFont, createCanvas, loadImage } = require('canvas');
const fs = require('fs');
const args = process.argv.slice(2)
const canvas = createCanvas(600, 200);
const context = canvas.getContext('2d');

create();

async function create() {
    var background;
    if (args[1] === "true") {
        try {
          background = await loadImage('linked player/'+args[2]+'/background.png');
        } catch (error) {
            background = await loadImage('res/background.png');
        }
    } else {
        background = await loadImage('res/background.png');
    }
    var level = await loadImage('level.png');
    var ap = await loadImage('ap.png');

    registerFont('res/font.ttf', { family: 'default' });
    context.drawImage(background, 0, 0, 600, 400);


    context.fillStyle = "rgba(0, 0, 0, 0.4)";
    context.fillRect(0, 0, 600, 400);

    context.drawImage(level, 20, 80, 560, 25);
    context.drawImage(ap, 20, 140, 560, 25);



    context.shadowColor = "black";
    context.shadowBlur = 10;
    context.shadowOffsetX = 2;
    context.shadowOffsetY = 2;
    context.font = 'regular 26pt ';
    context.textAlign = 'center';
    context.fillStyle = '#fff';
    context.fillText(args[0], 300, 40);
    if (args[2] === "75203801f5a54ba6baa691523aa15848") {
        context.font = 'regular 20pt ';
        context.fillText("eeeeee", 300, 70);
    }
    if (args[2] === "16d455475ddf42d48e9ffdb45cd69fa5") {
        context.font = 'regular 20pt ';
        context.fillText("The Champion himself", 300, 70);
    }
    if (args[2] === "983b1593a9a443ab8f2a15e0a65f720f") {
        context.font = 'regular 20pt ';
        context.fillText("No Fly Queen", 300, 70);
    }
    if (args[2] === "c46f6438006049d4830ca6fa732303fc") {
        context.font = 'regular 20pt ';
        context.fillText("HitW Elder", 300, 70);
    }
    if (args[2] === "b00d07b27984424db8f2d96c1e4aace5") {
        context.font = 'regular 20pt ';
        context.fillText("Community Father", 300, 70);
    }
    const buffer = canvas.toBuffer('image/png');
    fs.writeFileSync('./profile.png', buffer);
}
