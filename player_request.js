const Hypixel = require('node-hypixel');
const hypixel = new Hypixel('XXXXXXXXXXXXXXXX');
const args = process.argv.slice(2)

hypixel.getPlayerByUsername(args[0]).then(player => {
    console.log(player);
});
