package discordbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import discordbot.network.Database;



public class App {
    
    public final static String TOKEN = "set to your key";
    // private final static long PERMISSIONS = 11140140097L;
    public static final DiscordApi API = new DiscordApiBuilder()
        .setToken(TOKEN)
        .login()
        .join();

    public final static Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .serializeNulls()
        .setPrettyPrinting()
        .create();

    public static void main(String[] args) {
        
        Database.getOrCreate().put(Database.getOrCreate(), "database");

        Database fromTheDead = Database.getOrCreate().get("database", Database.class);


        // System.out.println(getApi().createBotInvite(new PermissionsImpl(PERMISSIONS)));

        Listeners.create();

    }

    // TODO make real tests or something... lol
    public String getGreeting() {
        return "Hello World!";
    }

}
