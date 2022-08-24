package discordbot.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.javacord.api.entity.user.User;

import discordbot.App;
import discordbot.Config;
import discordbot.Locations;
import discordbot.local.Filesystem;

public class Database {

    // private final URL DB_URL;
    private static Database database = null;
    private final String uri;


    private Database(Config config) {
        this.uri = config.DB_LOCATION;
    }

    public static Database getOrCreate() {

        if (database == null) {
            
            // hopefully the file exists... // FIXME
            String fileConfig = Filesystem.readFile(Locations.Local.CONFIG_FILE.get());
            Config config = App.GSON.fromJson(fileConfig, Config.class);
            
            Database db = new Database(config);
            database = db;
        } 
        return database;
    }

    private String getUri() { return uri; }
            
    /*
     * 
     *  Get & Put
     * 
     */

    public void put(Object obj, String name) {

        String json = App.GSON.toJson(obj);
            // TODO wash name
        
            HttpRequest request = HttpRequest.newBuilder()
                  .uri(URI.create(String.format("%s/%s", this.getUri(), name)))
                  .PUT(BodyPublishers.ofString(json))
                  .build();

            HttpClient client = HttpClient.newHttpClient();
            try {
                client.send(request, BodyHandlers.ofString());

                
            } catch (IOException | InterruptedException e) { e.printStackTrace(); }
    }

    public <T> T get(String name, Class<T> type) {
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("%s/%s", this.getUri(), name)))
            .GET()
            .build();

        HttpClient client = HttpClient.newHttpClient();
        
        try {
            
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return App.GSON.fromJson(response.body(), type);

        } catch (IOException | InterruptedException e) { e.printStackTrace(); }

        return null;
    }

    /**
     * 
     * @param id Name of the user you wish to get info on
     * @return User
     */
    public Person getUser(String id) {
        return get(id, Person.class);
    }


    // TODO get specific traits from the database without gathering the whole object
}