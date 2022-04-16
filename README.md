![splash](https://user-images.githubusercontent.com/30992311/133887063-18df67a6-6f68-40ab-b74f-5c3b8d51e376.png)

[![Codacy Badge](https://img.shields.io/codacy/grade/aa829b4c324a4e5f85b98afe85030317?logo=codacy)](https://www.codacy.com/gh/Blackoutburst/Wally/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Blackoutburst/Wally&amp;utm_campaign=Badge_Grade)
![Language](https://img.shields.io/github/languages/top/Blackoutburst/Wally?&logo=java&color=orange)
[![Release](https://img.shields.io/github/release/Blackoutburst/Wally.svg)](https://github.com/Blackoutburst/Wally/releases)
![Size](https://img.shields.io/github/repo-size/Blackoutburst/Wally)
[![Patreon](https://img.shields.io/badge/Support-Patreon-orange?&logo=patreon)](https://www.patreon.com/Blackoutburst)
[![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/Blackoutburst/Wally/Java%20CI%20with%20Gradle/master?logo=github)](https://github.com/Blackoutburst/Wally/actions)
[![License](https://img.shields.io/github/license/Blackoutburst/Wally.svg)](LICENSE)



# Important
Currently under complete rework, a lot is planned and this will be done in a few months due to limited time.\
Contribution are still welcomed but are less likely to be accepted in the current state.

# Wally
Wally is a discord bot that allow you to check your Hypixel Hole in the Wall stats, he contains much more features such as a score tracker, customizable user profile, leaderboards and much more!

## Prerequsites
* [Java 8+](https://www.java.com/) - Java is necessary to run the bot since it's made with Java
* [A config file](https://github.com/Blackoutburst/Wally/blob/master/config/config.json) - see the [config.json](https://github.com/Blackoutburst/Wally/blob/master/config/config.json) file and modify it to your needs
* [A Hypixel key](https://hypixel.net/) - In order to get the data from hypixel you need a key
* [A Discord bot](https://discord.com/developers/) - To send an receive messages via discord you need a bot and a token for that bot
* [A .env file](https://github.com/Blackoutburst/Wally/blob/master/config/.env.example) To put confidential values such as API key or discord token

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
