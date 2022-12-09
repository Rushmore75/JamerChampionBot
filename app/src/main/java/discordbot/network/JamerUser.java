package discordbot.network;

/**
 * Holds a user's data
 */
public class JamerUser {
    public final String id;
    public final Integer totalWins;
    public final Integer totalLosses;
    public final Integer streakWins;
    public final Integer streakLosses;

    public JamerUser(String id, int tW, int tL, int sW, int sL) {
        this.id = id;
        totalWins = tW;
        totalLosses = tL;
        streakWins = sW;
        streakLosses = sL;
    }

}
