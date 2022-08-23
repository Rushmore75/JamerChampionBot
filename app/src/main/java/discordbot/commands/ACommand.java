package discordbot.commands;

import java.util.function.Consumer;
import org.javacord.api.interaction.SlashCommandInteraction;

public abstract class ACommand {
    
    // default values for all commands
    private final String commandId;
    private final Consumer<SlashCommandInteraction> function;
    
    // constructor that will ultimately be called by all children
    protected ACommand(String id, Consumer<SlashCommandInteraction> fun) {
        commandId = id;
        function = fun;
    }

    public String getCommandId() {
        return commandId;
    }

    public Consumer<SlashCommandInteraction> getFunction() {
        return function;
    }

    // java did a bad. I want an abstract & static method :(
    // public static abstract ACommand get();

}
