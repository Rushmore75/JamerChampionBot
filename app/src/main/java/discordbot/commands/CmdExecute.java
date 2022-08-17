package discordbot.commands;

import java.util.HashMap;
import java.util.function.Consumer;
import org.javacord.api.interaction.SlashCommandInteraction;

import discordbot.commands.commands.Challenge;

public class CmdExecute {

    public static HashMap<String, Consumer<SlashCommandInteraction>> registerCommands() {
        // change Consumer's type as needed, this should work for now.
        HashMap<String, Consumer<SlashCommandInteraction>> hm = new HashMap<>();
        
        hm.put(Challenge.get().getCommandId(), Challenge.get().getFunction());

        return hm;
    }

}