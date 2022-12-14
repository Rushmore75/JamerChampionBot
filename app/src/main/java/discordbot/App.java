package discordbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import discordbot.local.Filesystem;

// TODO debug messages


public class App {

    // log4j logger
    public static final Logger LOGGER = LogManager.getLogger();
    // load gson
    public final static Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .serializeNulls()
        .setPrettyPrinting()
        .create();

    // load config
    public final static Config CONFIG = App.GSON.fromJson(
        Filesystem.readFile(Locations.Local.CONFIG_PATH.get()),
        Config.class
        );

    // join discord
    public static final DiscordApi API = new DiscordApiBuilder()
        .setToken(CONFIG.DISCORD_TOKEN)
        .login()
        .join();

    public static void main(String[] args) {
        LOGGER.info("Starting app.");
    

        Listeners.create();

        while (true) {
            // keep thread alive
            // TODO should api not join?
        }
    }    
}
