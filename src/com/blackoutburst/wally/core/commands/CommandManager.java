package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    private static final Set<Command> commands = new HashSet<>();

    private static CommandManager instance = null;

    /**
     * Get the command prefix
     * We could use a static variable but the goal is to
     * have the configuration refreshed automatically
     *
     * @return the command prefix
     */
    public static String getPrefix() {
        return (ConfigManager.getInstance().loadValue("settings", "command prefix"));
    }

    /**
     * Get the Wally instance
     * @return the Wally instance
     */
    public static @Nonnull CommandManager getInstance() {
        if (instance == null)
            instance = new CommandManager();

        return (instance);
    }

    /**
     * Register Wally commands
     */
    private CommandManager() {
        register(PermissionLevel.MEMBER, new CommandPing(), "ping");
    }

    /**
     * Register new commands
     *
     * @param level command execution permission level
     * @param executable command executable
     * @param labels command label / alias
     */
    private void register(@Nonnull PermissionLevel level, @Nonnull CommandExecutable executable, @Nonnull String... labels) {
        commands.add(new Command(level, executable, labels));
    }

    /**
     * Execute a command
     *
     * This is art, but you do not have to touch this function at all
     * If you find yourself in the need to edit this then you probably did something wrong
     */
    public void runCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        for (Command command : commands) {
            for (String label : command.getLabels()) {
                if (commandName.equals(label)) {
                    if (commandSender.getPermissionLevel().getWeight() >= command.getPermissionLevel().getWeight()) {
                        command.getExecutable().onCommand(commandSender, commandName, args, guild, channel, message);
                        Wally.getInstance().logger.log(LogLevel.INFO, commandSender.getMember().getEffectiveName()+" used ["+label+"]");
                    } else {
                        message.reply(ConfigManager.getInstance().loadValue("replies", "missing permission")).queue();
                        Wally.getInstance().logger.log(LogLevel.INFO, commandSender.getMember().getEffectiveName()+
                                " miss permission to use ["+label+"] U:"+commandSender.getPermissionLevel().getWeight()+"/C:"+command.getPermissionLevel().getWeight());
                    }
                }
            }
        }
    }
}
