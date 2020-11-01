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
    context.font = 'regular 18pt ';
    context.textAlign = 'left';
    context.fillText("Wins: "+args[3], 150, 125);
    context.fillText("Walls cleared: "+args[4], 150, 175);
    context.fillText("Best qualification score: "+args[1], 150, 225);
    context.fillText("Best final score: "+args[2], 150, 275);
    context.fillText("Q/F total: "+args[5], 150, 325);



    const buffer = canvas.toBuffer('image/png');
    fs.writeFileSync('./stats.png', buffer);
}
