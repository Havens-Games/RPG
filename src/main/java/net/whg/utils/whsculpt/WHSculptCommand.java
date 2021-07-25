package net.whg.utils.whsculpt;

import net.whg.utils.cmdformat.CommandHandler;

public class WHSculptCommand extends CommandHandler {
    public WHSculptCommand() {
        actions.add(new ColorShuffleAction());
        actions.add(new RemoveKeepOneAction());
    }

    public String getName() {
        return "whsculpt";
    }
}
