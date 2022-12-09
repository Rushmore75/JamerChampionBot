package discordbot.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;


import discordbot.App;
import discordbot.Config;

@Deprecated
public class Database {

    private static Database database = null;
    private final String uri; // NULL
    private final String url_template = getUri() + "/users/%s"; // FIXME this doesn't cache the getUri() call, it executes it right away


    private Database(Config config) {
        this.uri = config.DB_LOCATION;
    }

    public static Database getOrCreate() {
        if (database == null) {
            Database db = new Database(App.CONFIG);
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
        // create the http request, then make a client from it.
        HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(String.format(url_template, name))) // TODO wash name
              .PUT(BodyPublishers.ofString(json))
              .build();
        HttpClient client = HttpClient.newHttpClient();
        // then ask the request
        try {
            client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) { e.printStackTrace(); }
    }


    public <T> T get(String name, Class<T> type) {
        // create request and tie it to a client
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format(url_template, name))) // TODO wash name, or guarantee it only comes from internal
            .GET()
            .build();
        HttpClient client = HttpClient.newHttpClient();
        // ask the request
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
    public JamerUser getUser(String id) {
        return get(id, JamerUser.class);
    }
}