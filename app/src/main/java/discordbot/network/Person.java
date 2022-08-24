package discordbot.network;

public class Person {
    public final String id;
    public final int totalWins;
    public final int totalLosses;
    public final int streakWins;
    public final int streakLosses;

    public Person(String id, int tW, int tL, int sW, int sL) {
        this.id = id;
        totalWins = tW;
        totalLosses = tL;
        streakWins = sW;
        streakLosses = sL;
    }

}
