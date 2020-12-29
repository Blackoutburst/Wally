const Hypixel = require('node-hypixel');
const hypixel = new Hypixel('');
const args = process.argv.slice(2)

hypixel.getPlayer(args[0]).then(player => {
    console.log(player);
});
