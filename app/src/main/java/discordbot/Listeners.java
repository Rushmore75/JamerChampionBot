package discordbot;

import java.util.HashMap;
import java.util.function.Consumer;
import org.javacord.api.interaction.SlashCommandInteraction;
import discordbot.commands.commands.Challenge;

public class Listeners {

    public static HashMap<String, Consumer<SlashCommandInteraction>> initCommands() {

        HashMap<String, Consumer<SlashCommandInteraction>> commands = new HashMap<>();
        // TODO change this to just go through the commands folder and grab all the commands.
        commands.put(Challenge.get().getCommandId(), Challenge.get().getFunction());

        return commands;
    }

    public static void create() {
        // Hashmap of all the commands
        HashMap<String, Consumer<SlashCommandInteraction>> commands = initCommands();
        
        // adding a listener to the discord bot
        App.API.addSlashCommandCreateListener(event -> {
                                        // event fired at the listener
 
            // normal implementation:
            //https://javacord.org/wiki/basic-tutorials/interactions/responding.html#slashcommand-interaction-only-response-methods

            // Any time the listener gets called the event will get passed into this function.
            // Then this hashmap will retrieve a command based on it's command ID.
            // Then the resulting command's code will get called in the `.accept()` method.
            // This is abstracted like this so that all the code for one command is place in the same file.
            commands
                .get(event.getSlashCommandInteraction().getCommandIdAsString())
                // I am using a raw get() function on an option. This
                // could end poorly but it shouldn't.
                .accept(event.getInteraction().asSlashCommandInteraction().get());

        });
    }
}
