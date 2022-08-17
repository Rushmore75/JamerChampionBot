package discordbot;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import javax.swing.plaf.ColorUIResource;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
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
        
        // TODO remove and stuff
        App.API.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }

            if (event.getMessageContent().equalsIgnoreCase("!help")) {
                User user = event.getMessage().getAuthor().asUser().get();
                Server server = event.getServer().get();
                String name = "King of the Jamers";

                event.getChannel().sendMessage("recieved");

                Long role = null;
                try {
                    role = server.createRoleBuilder()
                            .setColor(new ColorUIResource(12, 125, 255))
                            .setMentionable(true)
                            .setName(name)
                            .create()
                            .get()
                            .getId()
                            ;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (role != null) {
                    server.addRoleToUser(user, server.getRoleById(role).get());
                }
            }


        });

    }
}
