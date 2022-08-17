package discordbot;

import java.nio.file.Paths;


public class Locations {

    public static enum Local {
        PARENT_DIR  (Paths.get("").toAbsolutePath().getParent().toString()),
        
        USERS      ("/users"),
        USERS_FILE (PARENT_DIR.get() + USERS.get()),
        
        CONFIG      ("/config.json"),
        CONFIG_FILE (PARENT_DIR.get() + CONFIG.get());
        
        private final String string;
        private Local(String s) { 
            this.string = s;
        }
        public String get() { return string; }
        // public Path getAsPath() { return Paths.get(string); }
    }

    public static enum Network {

        DEFAULT_DB_URL ("http://127.0.0.1:8080/");
        
        private final String string;
        private Network(String s) { 
            this.string = s;
        }
        public String get() { return string; }
    }

}
