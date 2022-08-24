package discordbot.commands.messages;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import discordbot.network.Database;

import java.awt.Color;

public class ChallengeMsg {
    // what is a record in java?
    
    private enum Msgs {
        
        WINS                ("Wins 👑"),
        LOSSES              ("Losses ❌"),
        WINS_STREAK         ("(W Streak 🔥)"),
        LOSSES_STREAK       ("(L Streak 🌊)"),
        TITLE_CHAMPION      ("🛡️ Reigning Champion:"),
        TITLE_CHALLENGER    ("⚔️ Challenger:"),
        NONE                ("\u200B");

        private final String s;

        private Msgs(String s) {
            this.s = s;
        }

        public String get() {
            return s;
        }

    }

    public static EmbedBuilder generateDefender() {
        return new EmbedBuilder()

            .setColor(Color.CYAN)
            .setAuthor(Msgs.TITLE_CHAMPION.get())
            // TODO use some sort of database and collect the name there
            .setTitle("name")
            
            // line one
            .addInlineField(Msgs.WINS.get(), "value")
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.WINS_STREAK.get(), "value")

            // line two
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.LOSSES.get(), "value")
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            ;
    }

    public static EmbedBuilder generateChallenger(User user, Server server) {

        final String NAME = user.getName();
        final String ID = user.getIdAsString();
        String nickname = "";
        // TODO nickname is always false...
        Boolean hasNickname = user.getNickname(server).isPresent();

        if (hasNickname) {
            nickname = user.getNickname(server).get();
        }

        // Database.getOrCreate().get(ID, type)

        return new EmbedBuilder()
            .setColor(Color.RED)
            .setAuthor(Msgs.TITLE_CHALLENGER.get())
            // Sets the main name to be the nick name, if present. Then also puts the user's real name below.
            // But only if the person is nicked. Otherwise there is not extra name required.
            .setTitle(hasNickname ? nickname : NAME)
            .setDescription(hasNickname ? String.format("(%s)", user.getName()) : "")

            // line one
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.WINS.get(), "value")
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())

            // line two
            .addInlineField(Msgs.LOSSES.get(), "value")
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.LOSSES_STREAK.get(), "value")
            ;
    }

    public static EmbedBuilder generateWinLoss(User defendUser, User challengerUser) {
        // TODO (person 1 win:loss) / (person two win:loss)

        var defender = Database.getOrCreate().getUser(defendUser.getIdAsString());
        var challenger = Database.getOrCreate().getUser(challengerUser.getIdAsString());

        Float defWinLoss = (float) (defender.totalWins / defender.totalLosses);
        Float chaWinLoss = (float) (challenger.totalWins / challenger.totalLosses);
        Float winRatio = defWinLoss / chaWinLoss;

        return new EmbedBuilder()
        .setColor(Color.LIGHT_GRAY)
        .addInlineField("Defender W:L ", defWinLoss.toString())
        .addInlineField("Challenger W:L ", chaWinLoss.toString())
        .addInlineField("Chance of Success for Defender: ", winRatio.toString());
    }

}
