package discordbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import discordbot.local.Filesystem;
import discordbot.network.Database;



public class App {

    public final static Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .serializeNulls()
        .setPrettyPrinting()
        .create();

    public final static Config CONFIG = App.GSON.fromJson(
        Filesystem.readFile(Locations.Local.CONFIG_FILE.get()),
        Config.class
        );

    public static final DiscordApi API = new DiscordApiBuilder()
        .setToken(CONFIG.DISCORD_TOKEN)
        .login()
        .join();

    public static void main(String[] args) {
        
        var database = Database.getOrCreate();

        Listeners.create();

        while (true) {
            // keep thread alive
        }
    }    
}
