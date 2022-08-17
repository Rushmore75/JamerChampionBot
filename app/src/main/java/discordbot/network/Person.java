package discordbot.network;

public class Person {
    private static String id;
    private static int totalWins;
    private static int totalLosses;
    private static int streakWins;
    private static int streakLosses;
    

    public Person(String id) {
        this.id = id;
        totalWins = 0;
        totalLosses = 0;
        streakWins = 0;
        streakLosses = 0;
    }

    public Person(String id, int tW, int tL, int sW, int sL) {
        this.id = id;
        totalWins = tW;
        totalLosses = tL;
        streakWins = sW;
        streakLosses = sL;
    }

}
