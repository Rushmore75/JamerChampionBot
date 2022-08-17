package discordbot.local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import discordbot.App;

public class Filesystem {
    

    // TODO change to a builder pattern for fun?

    /**
     * 
     * @param location The directory in which the file is located
     * @param fileName The file name it's self (including file extension (if applicable))
     * @return Contents of the file
     * 
     */
    public static String readFile(String location, String fileName) {
        return readFile(location + fileName);
    }

    /**
     * 
     * @param location The directory in which the file is located
     * @param fileName The file name it's self (including file extension (if applicable))
     * @return Contents of the file
     * 
     */
    public static String readFile(String fullLocation) {
        byte[] raw = null;
        
        try {
            
            raw = Files.readAllBytes( Paths.get(fullLocation) );

        } catch (IOException e) { e.printStackTrace(); }
        
        return new String(raw);
    }

    /**
     *  File doesn't need to currently exist. This will
     *  create it if needed.
     * @param contents What to write into the file
     * @param location The directory where the file will be held
     * @param fileName The name of the file in the location
     * @return True if the write was a success, False if it failed
     */
    public static boolean writeFile(String contents, String location, String fileName) {

        Path fileLocation = Paths.get(location, fileName);
        Path fileLocPath = Paths.get(location);
        
        try {
            
            if (!Files.exists(fileLocation)) {
                Files.createDirectories(fileLocPath);
                Files.createFile(fileLocation);
            }
            Files.write(fileLocation, contents.getBytes(), StandardOpenOption.WRITE);
            return true;

        } catch (IOException e) { e.printStackTrace(); }
        
        return false;

    }

    /**
     * 
     * @param contents What to write into the file
     * @param location The directory where the file will be held
     * @param fileName The name of the file in the location
     * @return True if the write was a success, False if it failed
     */
    public static boolean writeFile(Object contents, String location, String fileName) {
        return writeFile(App.GSON.toJson(contents), location, fileName);
    }
}
