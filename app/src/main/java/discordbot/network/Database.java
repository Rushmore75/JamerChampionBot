package discordbot.network;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import discordbot.App;
import discordbot.Config;



public class Database {

    // private final URL DB_URL;
    private static Database database = null; 
    private final String URI;


    private Database(Config config) {
        this.URI = config.DB_LOCATION;
    }

    /**
     * Returns the current working database if the method has been called 
     * before. Otherwise it will create a new Database object which will
     * be returned on subsequent calls to this method.
     * @return Database (singleton)
     */
    public static Database getOrCreate() {

        if (database == null) {
                        
            Database db = new Database(App.CONFIG);
            database = db;
        } 
        return database;
    }

    private String getUri() { return URI; }
            
    /*
     * 
     *  Get & Put
     * 
     */

    /**
     * HTTP put request
     * @param obj gets converted to json and put in the body
     * @param name 
     */
    public void put(Object obj, String name) {

        /*
         * Name washing isn't required as the database does this already.
         */
        String json = App.GSON.toJson(obj);
        
            HttpRequest request = HttpRequest.newBuilder()
                  .uri(java.net.URI.create(String.format("%s/%s", this.getUri(), name)))
                  .PUT(BodyPublishers.ofString(json))
                  .build();

            HttpClient client = HttpClient.newHttpClient();
            try {
                client.send(request, BodyHandlers.ofString());

                
            } catch (IOException | InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * HTTP get request
     * @param <T> Type of "type"
     * @param name Name of item you are soliciting from database
     * @param type Expected type returned 
     * @return T
     */
    public <T> T get(String name, Class<T> type) {
        
        HttpRequest request = HttpRequest.newBuilder()
        // TODO this string formatting has changed and may change more times
        // on the database system.
            .uri(java.net.URI.create(String.format("%s/%s", this.getUri(), name)))
            .GET()
            .build();

        HttpClient client = HttpClient.newHttpClient();
        
        try {
            
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return App.GSON.fromJson(response.body(), type);

        } catch (IOException | InterruptedException e) { e.printStackTrace(); }

        return null;
    }


    // TODO get specific traits from the database without gathering the whole object
}