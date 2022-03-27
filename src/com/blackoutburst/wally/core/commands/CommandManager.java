package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    private static final Set<Command> commands = new HashSet<>();

    private static CommandManager instance = null;

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
    private void register(PermissionLevel level, CommandExecutable executable, String... labels) {
        commands.add(new Command(level, executable, labels));
    }

    /**
     * Execute a command
     */
    public void runCommand(@Nonnull Member commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        for (Command command : commands) {
            for (String label : command.getLabels()) {
                if (commandName.equals(label)) { // Add permission check here later
                    command.getExecutable().onCommand(commandSender, commandName, args, guild, channel, message);
                }
            }
        }
    }
}
