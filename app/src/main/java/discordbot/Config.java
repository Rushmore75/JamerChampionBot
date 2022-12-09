package discordbot;


/**
 * The application's configuration
 */
public class Config {
    public final String DB_LOCATION;
    public final String DISCORD_TOKEN;
    public final Long PERMISSIONS;


    public Config(String dbUrl, String discordString, Long permissions) {
        this.DB_LOCATION = dbUrl;
        this.DISCORD_TOKEN = discordString;
        this.PERMISSIONS = permissions;
    }


}
