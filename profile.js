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
    if (args[0] === "doguette") {
        context.font = 'regular 24pt ';
        context.fillText("eeeeee", 300, 70);
    }
    const buffer = canvas.toBuffer('image/png');
    fs.writeFileSync('./profile.png', buffer);
}
