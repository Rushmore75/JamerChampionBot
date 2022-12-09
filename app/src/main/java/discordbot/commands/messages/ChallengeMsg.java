package discordbot.commands.messages;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import discordbot.network.Database;
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

    public static EmbedBuilder generateDefender(String name) {

        JamerUser user = Database.getOrCreate().get(name, JamerUser.class);

        return new EmbedBuilder()

            .setColor(Color.CYAN)
            .setAuthor(Msgs.TITLE_CHAMPION.get())
            .setTitle(user.id)
            
            // line one
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            .addInlineField(Msgs.WINS.get(), user.totalWins.toString())
            .addInlineField(Msgs.NONE.get(), Msgs.NONE.get())
            

            // line two
            .addInlineField(Msgs.LOSSES.get(),      user.totalLosses.toString())
            .addInlineField(Msgs.NONE.get(),        Msgs.NONE.get())
            .addInlineField(Msgs.WINS_STREAK.get(), user.streakWins.toString())
            ;
    }

    public static EmbedBuilder generateChallenger(User user, Server server) {

        final String NAME = user.getName();
        final String ID   = user.getIdAsString();
        String nickname   = "";
        // TODO nickname is always false...
        // Don't worry about this for now, just use the set name
        Boolean hasNickname = user.getNickname(server).isPresent();

        if (hasNickname) {
            nickname = user.getNickname(server).get();
        }

        JamerUser userData = Database.getOrCreate().get(ID, JamerUser.class);

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

    public static EmbedBuilder generateWinLoss(User defendUser, User challengerUser) {
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
