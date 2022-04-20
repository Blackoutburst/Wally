package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        register(PermissionLevel.STAFF, new CommandSay(), "say");
        register(PermissionLevel.STAFF, new CommandLink(), "link");
        register(PermissionLevel.STAFF, new CommandUnlink(), "unlink");
        register(PermissionLevel.ADMIN, new CommandSetLogLevel(), "setloglevel");
        register(PermissionLevel.ADMIN, new CommandReloadUser(), "reloaduser", "reloadusers");
        register(PermissionLevel.ADMIN, new CommandSetPermissionLevel(), "setpermissionlevel", "setperms");
        register(PermissionLevel.ADMIN, new CommandGetConfig(), "getconfig", "getconfiguration");
        register(PermissionLevel.ADMIN, new CommandSetConfig(), "setconfig", "setconfiguration");
        register(PermissionLevel.MEMBER, new CommandPoll(), "poll");

        Wally.getInstance().logger.log(LogLevel.INFO, "Command registered!");
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

    private void checkForCloseCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull Message message) {
        int lowestWeight = Integer.MAX_VALUE;

        String closest = null;
        int inputWeight = commandName.chars().sum();

        for (final Command c : commands) {
            for (final String l : c.getLabels()) {
                final int tempWeight = l.chars().sum();

                if (Math.abs(inputWeight - tempWeight) < lowestWeight) {
                    lowestWeight = Math.abs(inputWeight - tempWeight);
                    closest = l;
                }
            }
        }
        if (lowestWeight > 100) closest = null;
        if (closest == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "unknown command").replace("%command_name%", commandName)).queue();
            return;
        }

        message.reply(ConfigManager.getInstance().loadValue("replies", "unknown command").replace("%command_name%", commandName)+'\n'+
                ConfigManager.getInstance().loadValue("replies", "close command").replace("%command_name%", closest))
                .setActionRow(Button.primary(commandSender.getMember().getId()+":"+message.getId(), "Yes run "+closest),
                        Button.danger(commandSender.getMember().getId()+":"+UUID.randomUUID(), "No")).queue();

    }

    /**
     * Execute a command
     *
     * This is art, but you do not have to touch this function at all
     * If you find yourself in the need to edit this then you probably did something wrong
     */
    public void runCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        channel.sendTyping().queue();

        for (Command command : commands) {
            for (String label : command.getLabels()) {
                if (commandName.equalsIgnoreCase(label)) {
                    if (commandSender.getPermissionLevel().getWeight() >= command.getPermissionLevel().getWeight()) {
                        command.getExecutable().onCommand(commandSender, commandName, args, guild, channel, message);
                        Wally.getInstance().logger.log(LogLevel.INFO, commandSender.getMember().getEffectiveName()+" used ["+label+"]");
                    } else {
                        message.reply(ConfigManager.getInstance().loadValue("replies", "missing permission")).queue();
                        Wally.getInstance().logger.log(LogLevel.INFO, commandSender.getMember().getEffectiveName()+" miss permission to use ["+label+"] U:"+commandSender.getPermissionLevel().getWeight()+"/C:"+command.getPermissionLevel().getWeight());
                    }
                    return;
                }
            }
        }
        checkForCloseCommand(commandSender, commandName, message);
    }
}
