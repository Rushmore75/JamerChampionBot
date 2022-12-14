package discordbot.commands.messages;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import discordbot.App;
import discordbot.network.JamerUser;
import discordbot.network.NeoDatabase;

import java.awt.Color;

public class ChallengeMsg {
    
    private enum Msgs {
        
        WINS                ("Wins 👑"),
        LOSSES              ("Losses ❌"),
        WINS_STREAK         ("(W Streak 🔥)"),
        LOSSES_STREAK       ("(L Streak 🌊)"),
        TITLE_CHAMPION      ("🛡️ Reigning Champion:"),
        TITLE_CHALLENGER    ("⚔️ Challenger:"),
        NONE                ("\u200B");

        private final String s;

        private Msgs(String s) { this.s = s; }

        public String get() { return s; }

    }
    /**
     * Generate the defender's message based off the given user input.
     * @param id The id of the user to generate text for
     * @return EmbedBuilder, to be used to create Discord embeds
     */
    public static EmbedBuilder generateDefender(User user, Server server) {
        App.LOGGER.info("Generating defender's message.");

        // TODO add server
        JamerUser localUser = NeoDatabase.getUser(user.getIdAsString());

        return new EmbedBuilder()

            .setColor(Color.CYAN)
            .setAuthor(Msgs.TITLE_CHAMPION.get())
            .setTitle(user.getName())
            
            // line one
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.WINS.get(), localUser.totalWins.toString())
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            

            // line two
            .addInlineField(Msgs.LOSSES.get(),      localUser.totalLosses.toString())
            .addInlineField(Msgs.NONE.get(),        Msgs.NONE.get())
            .addInlineField(Msgs.WINS_STREAK.get(), localUser.streakWins.toString())
            ;
    }

    /**
     * Generate the challenger's message based off the givin user input.
     * @param user The user to generate text for
     * @param server the server id
     * @return EmbedBuilder, to be used to create Discord embeds
     */
    public static EmbedBuilder generateChallenger(User user, Server server) {
        App.LOGGER.info("Generating challenger's message.");

        final String NAME = user.getName();
        final String ID   = user.getIdAsString();
        String nickname   = "";
        // TODO nickname is always false...
        // Don't worry about this for now, just use the set name
        Boolean hasNickname = user.getNickname(server).isPresent();

        if (hasNickname) {
            nickname = user.getNickname(server).get();
        }

        // JamerUser userData = Database.getOrCreate().get(ID, JamerUser.class);
        JamerUser userData = NeoDatabase.getUser(ID);

        return new EmbedBuilder()
            .setColor(Color.RED)
            .setAuthor(Msgs.TITLE_CHALLENGER.get())
            // Sets the main name to be the nick name, if present. Then also puts the user's real name below.
            // But only if the person is nicked. Otherwise there is not extra name required.
            .setTitle(hasNickname ? nickname : NAME)
            .setDescription(hasNickname ? String.format("(%s)", user.getName()) : "")

            // line one
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.WINS.get(), userData.totalWins.toString())
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())

            // line two
            .addInlineField(Msgs.LOSSES.get(),          userData.totalLosses.toString())
            .addInlineField(Msgs.NONE.get(),            Msgs.NONE.get())
            .addInlineField(Msgs.LOSSES_STREAK.get(),   userData.streakLosses.toString())
            ;
    }

    /**
     * Generate the probability that either user will win.
     * @param defendUser 
     * @param challengerUser
     * @return EmbedBuilder, so you can use it inline with other EmbedBuilders.
     */
    public static EmbedBuilder generateWinLoss(User defendUser, User challengerUser) {
        App.LOGGER.info("Generating win : loss chance");
        
        // TODO (person 1 win:loss) / (person two win:loss)

        var defender = NeoDatabase.getUser(defendUser.getIdAsString());
        var challenger = NeoDatabase.getUser(challengerUser.getIdAsString());

        Float defWinLoss    = (float) (defender.totalWins   / defender.totalLosses);
        Float chaWinLoss    = (float) (challenger.totalWins / challenger.totalLosses);
        Float winRatio      = defWinLoss / chaWinLoss;

        return new EmbedBuilder()
        .setColor(Color.LIGHT_GRAY)
        .addInlineField("Defender W:L ", defWinLoss.toString())
        .addInlineField("Challenger W:L ", chaWinLoss.toString())
        .addInlineField("Chance of Success for Defender: ", winRatio.toString());
    }

}
