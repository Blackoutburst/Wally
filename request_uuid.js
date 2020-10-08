const request = require('request');
const args = process.argv.slice(2)

request('https://api.mojang.com/users/profiles/minecraft/'+args[0], function (error, response, body) {
    if (body == ""){
        console.log("null");
    }
    if (!error && response.statusCode == 200) {
        var uuid = body.split("\"");
        console.log(uuid[7]);
    }
});
