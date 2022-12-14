package discordbot;

import java.nio.file.Paths;

/**
 * Holds all of the app's location data, so that it is centralized
 */
public class Locations {

    public static enum Local {
        PARENT_DIR  (Paths.get("").toAbsolutePath().getParent().toString()),
        
        USERS      ("/users"),
        USERS_PATH (PARENT_DIR.get() + USERS.get()),
        
        CONFIG      ("/config.json"),
        CONFIG_PATH (PARENT_DIR.get() + CONFIG.get());
        
        private final String string;
        private Local(String s) { 
            this.string = s;
        }
        public String get() { return string; }
        // public Path getAsPath() { return Paths.get(string); }
    }

    public static enum Network {
        // create table users (UserID varchar(18), WINS int, LOSSES int, WINS_STREAK int, LOSSES_STREAK int);
        // TODO table should be the id of the server, so it works multiserver.
        USER_TABLE ("users"),
        
        DEFAULT_DB_URL (App.CONFIG.DB_LOCATION);
        
        private final String string;
        private Network(String s) { 
            this.string = s;
        }
        public String get() { return string; }
    }

}

