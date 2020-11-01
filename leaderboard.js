const { registerFont, createCanvas, loadImage } = require('canvas');
const fs = require('fs');
const args = process.argv.slice(2)
const canvas = createCanvas(600, 400);
const context = canvas.getContext('2d');
const data = args[0].split("/");

create();

async function create() {
    var background = await loadImage('res/background.png');

    registerFont('res/font.ttf', { family: 'default' });
    context.drawImage(background, 0, 0, 600, 400);

    context.fillStyle = "rgba(0, 0, 0, 0.4)";
    context.fillRect(0, 0, 600, 400);

    context.shadowColor = "black";
    context.shadowBlur = 10;
    context.shadowOffsetX = 2;
    context.shadowOffsetY = 2;
    context.font = 'regular 24pt ';
    context.textAlign = 'center';
    context.fillStyle = '#fff';
    context.fillText(args[1], 300, 40);
    context.font = 'regular 19pt ';
    context.fillText(data[0], 300, 75);
    context.fillText(data[1], 300, 110);
    context.fillText(data[2], 300, 145);
    context.fillText(data[3], 300, 180);
    context.fillText(data[4], 300, 215);
    context.fillText(data[5], 300, 250);
    context.fillText(data[6], 300, 285);
    context.fillText(data[7], 300, 320);
    context.fillText(data[8], 300, 355);
    context.fillText(data[9], 300, 390);


    const buffer = canvas.toBuffer('image/png');
    fs.writeFileSync('./lead.png', buffer);
}
