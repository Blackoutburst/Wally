const { registerFont, createCanvas, loadImage } = require('canvas');
const fs = require('fs');
const args = process.argv.slice(2)
const canvas = createCanvas(600, 400);
const context = canvas.getContext('2d');

create();

async function create() {
    var background;
    if (args[6] === "true") {
        try {
          background = await loadImage('linked player/'+args[7]+'/background.png');
        } catch (error) {
            background = await loadImage('res/background.png');
        }
    } else {
        background = await loadImage('res/background.png');
    }
    var winsIcon = await loadImage('res/win.png');
    var wallsIcon = await loadImage('res/wall.png');
    var qualIcon = await loadImage('res/q.png');
    var finIcon = await loadImage('res/f.png');
    var totalIcon = await loadImage('res/total.png');

    registerFont('res/font.ttf', { family: 'default' });
    context.drawImage(background, 0, 0, 600, 400);


    context.fillStyle = "rgba(0, 0, 0, 0.4)";
    context.fillRect(0, 0, 600, 400);

    context.drawImage(winsIcon, 100, 105, 24, 24);
    context.drawImage(wallsIcon, 100, 155, 24, 24);
    context.drawImage(qualIcon, 100, 205, 24, 24);
    context.drawImage(finIcon, 100, 255, 24, 24);
    context.drawImage(totalIcon, 100, 305, 24, 24);



    context.shadowColor = "black";
    context.shadowBlur = 10;
    context.shadowOffsetX = 2;
    context.shadowOffsetY = 2;
    context.font = 'regular 26pt ';
    context.textAlign = 'center';
    context.fillStyle = '#fff';
    context.fillText(args[0], 300, 40);
    if (args[7] === "75203801f5a54ba6baa691523aa15848") {
        context.font = 'regular 20pt ';
        context.fillText("hhhhhhhhhh", 300, 70);
    }
    if (args[7] === "16d455475ddf42d48e9ffdb45cd69fa5") {
        context.font = 'regular 20pt ';
        context.fillText("The Champion himself", 300, 70);
    }
    if (args[7] === "983b1593a9a443ab8f2a15e0a65f720f") {
        context.font = 'regular 20pt ';
        context.fillText("No Fly Queen", 300, 70);
    }
    if (args[7] === "c46f6438006049d4830ca6fa732303fc") {
        context.font = 'regular 20pt ';
        context.fillText("HitW Elder", 300, 70);
    }
    if (args[7] === "b00d07b27984424db8f2d96c1e4aace5") {
        context.font = 'regular 20pt ';
        context.fillText("Community Father", 300, 70);
    }
    if (args[7] === "bedb53e2dd754786a2ac1ec80023aabe") {
        context.font = 'regular 20pt ';
        context.fillText("Simon's Cube Slave", 300, 70);
    }

    context.font = 'regular 18pt ';
    context.textAlign = 'left';
    context.fillText("Wins: "+args[3]+" (#"+args[8]+")", 150, 125);
    context.fillText("Walls cleared: "+args[4]+" (#"+args[9]+")", 150, 175);
    context.fillText("Best qualification score: "+args[1]+" (#"+args[10]+")", 150, 225);
    context.fillText("Best final score: "+args[2]+" (#"+args[11]+")", 150, 275);
    context.fillText("Q/F total: "+args[5]+" (#"+args[12]+")", 150, 325);



    const buffer = canvas.toBuffer('image/png');
    fs.writeFileSync('./stats.png', buffer);
}
