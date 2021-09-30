package net.whg.utils.cmdformat;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * A basic subcommand that lists out all available subcommands and their usage
 * text.
 */
public class HelpSubcommand extends Subcommand {
    private final List<Subcommand> actions;
    private final String commandNamespace;

    /**
     * Creates a new HelpSubcommand.
     * 
     * @param actions          - A list of all available subcommands.
     * @param commandNamespace - The command namespace to write in the description
     *                         text.
     */
    public HelpSubcommand(List<Subcommand> actions, String commandNamespace) {
        this.actions = actions;
        this.commandNamespace = commandNamespace;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        listActions(sender);
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getName() {
        return "help";
    }

    /**
     * Sends a list of all available subcommands to the command sender as a
     * formatted message.
     * 
     * @param sender - The command sender to send the message to.
     */
    private void listActions(CommandSender sender) {
        var lines = new String[actions.size() + 1];
        lines[0] = ChatColor.GRAY + "Available Actions:";

        for (int i = 0; i < actions.size(); i++) {
            var action = actions.get(i);
            lines[i + 1] = String.format("%s/%s %s%s %s", ChatColor.DARK_AQUA, commandNamespace, ChatColor.GRAY,
                    action.getName(), action.getUsage());
        }

        sender.sendMessage(lines);
    }
}
