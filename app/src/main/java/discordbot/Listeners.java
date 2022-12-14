package discordbot;

import java.util.HashMap;
import java.util.function.Consumer;
import org.javacord.api.interaction.SlashCommandInteraction;
import discordbot.commands.commands.Challenge;

/**
 * Holds a map of all the commands and their data. It registers them
 * with the discord api so they are useable in app.
 */
public class Listeners {

    public static HashMap<String, Consumer<SlashCommandInteraction>> initCommands() {

        HashMap<String, Consumer<SlashCommandInteraction>> commands = new HashMap<>();
        // TODO change this to just go through the commands folder and grab all the commands.

        App.LOGGER.info("Adding " + Challenge.class.getName() + " command.");
        commands.put(Challenge.get().getCommandId(), Challenge.get().getFunction());

        return commands;
    }


    /**
     * Registers/creates all commands
     */
    public static void create() {
        

        // Hashmap of all the commands
        HashMap<String, Consumer<SlashCommandInteraction>> commands = initCommands();
        
        App.LOGGER.info("Adding command listener.");
        // adding a listener to the discord bot
        App.API.addSlashCommandCreateListener(event -> {
                                        // event fired at the listener
            App.LOGGER.info(
                "Received command event from "
                + event.getSlashCommandInteraction().getCommandIdAsString()
                + " command"
                );

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
