package discordbot.commands.commands;

import java.util.Arrays;
import java.util.function.Consumer;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import discordbot.App;
import discordbot.commands.ACommand;
import discordbot.commands.messages.ChallengeMsg;

public class Challenge extends ACommand {

    private static Challenge self = null;
    /**
     * Singleton, this means no real overhead is created by calling this
     * method multiple times.
     * @return the command
     */
    public static Challenge get() {
        if (self != null) {
            return self;
        } else {
            return new Challenge(
                /**
                 *      Creation of the ID
                 *      arg 0
                 **/
                SlashCommand.with("challenge", "Challenge someone",
                    Arrays.asList(
                        // TODO add ability to optionally challenge a person or just challenge the champion
                        SlashCommandOption.createWithChoices(
                            SlashCommandOptionType.USER,
                            "target",
                            "Whom you challenge!",
                            true)
                    )
                )
                .setEnabledInDms(false)
                .createGlobal(App.API)
                .join()
                .getIdAsString(),

                /**
                 *      Creation of the function to be run when the listener
                 *      hears that the command is called from Discord.
                 * 
                 *      arg 1
                 **/
                /*
                 * This will be the code that gets fired when the command "Challenge"
                 * is called.
                 */
                interaction -> {       
                   // extract command contents
                   interaction.getOptionByIndex(0).ifPresent(option -> {   
                        // get the targeted users (not the sender)
                        option.getUserValue().ifPresent(targetUser -> {
                            // get the channel the command was issued in, so it can be send back there.
                            interaction.getChannel().ifPresent(channel -> {
                                new MessageBuilder()
                                    .setEmbeds(
                                     // TODO switch one of the users lol
                                        ChallengeMsg.generateChallenger(interaction.getUser(), interaction.getServer().get()),
                                        ChallengeMsg.generateDefender(targetUser, interaction.getServer().get())
                                    )
                                    .send(channel); 
                            });
                        });

                       interaction.createImmediateResponder()
                           .append("\u200B")
                           .respond();
                   });
               });
        }
    }
    private Challenge(String id, Consumer<SlashCommandInteraction> fun) {
        super(id, fun);
    }
}
