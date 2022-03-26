![splash](https://user-images.githubusercontent.com/30992311/133887063-18df67a6-6f68-40ab-b74f-5c3b8d51e376.png)

[![License](https://img.shields.io/github/license/Blackoutburst/Wally.svg)](LICENSE)
[![Release](https://img.shields.io/github/release/Blackoutburst/Wally.svg)](https://github.com/Blackoutburst/Wally/releases)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/aa829b4c324a4e5f85b98afe85030317)](https://www.codacy.com/gh/Blackoutburst/Wally/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Blackoutburst/Wally&amp;utm_campaign=Badge_Grade)
[<img src="https://img.shields.io/badge/Webpage-Wally-blueviolet">](https://www.blackoutburst.com/wally.html)
[<img src="https://img.shields.io/badge/Support-Patreon-orange?&logo=patreon">](https://www.patreon.com/Blackoutburst)

# Important
A complete rework is planned soon to allow new features and more stability

# Wally
Wally is a bot for discord that is used to link with hypixel. 

## Prerequsites
* [Java 8+](https://www.java.com/) - Java is necessary to run the bot since it's made with Java
* [A config file](https://github.com/Blackoutburst/Wally/blob/master/config/config.json) - see the [config.json](https://github.com/Blackoutburst/Wally/blob/master/config/config.json) file and modify it to your needs
* [A Hypixel key](https://hypixel.net/) - In order to get the data from hypixel you need a key
* [A Discord bot](https://discord.com/developers/) - To send an receive messages via discord you need a bot and a token for that bot

## Building
Open a command line and go into the directory and run
```
gradlew buildDependents
```

## Running
Locate the generated jar file should be inside `repo/build/libs` and run
```
java -jar Wally-x.x.x-all.jar
```

## Contributing
See [Contributing.md](https://github.com/Blackoutburst/Wally/blob/master/CONTRIBUTING.md)
