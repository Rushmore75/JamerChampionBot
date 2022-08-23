package discordbot;

import java.util.HashMap;
import java.util.function.Consumer;
import org.javacord.api.interaction.SlashCommandInteraction;
import discordbot.commands.CmdExecute;

public class Listeners {

    public static void create() {

        HashMap<String, Consumer<SlashCommandInteraction>> commands = CmdExecute.registerCommands();

        App.API.addSlashCommandCreateListener(event -> {

            commands
                .get(event.getSlashCommandInteraction().getCommandIdAsString())
                // TODO this line may break it, I am using a raw get() command on an option, I don't
                // think this should ever be bad, but it may happen.
                .accept(event.getInteraction().asSlashCommandInteraction().get());

        });        
                // Long role = null;
                //     role = server.createRoleBuilder()
                //             .setColor(new ColorUIResource(12, 125, 255))
                //             .setMentionable(true)
                //             .setName(name)
                //             .create()
                //             .get()
                //             .getId()
                //             ;

    }
}
