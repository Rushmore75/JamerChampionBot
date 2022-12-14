package discordbot.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import discordbot.App;
import discordbot.Locations;

public class NeoDatabase {
    // TODO this should be brought out into an interface which this would implement

    /**
     * Store a user's data (overwriting old data)
     * @param user The user who you which to store
     */
    public static void put(JamerUser user) {
        App.LOGGER.info("Putting " + user.id + " into the database.");
        try {
            Connection connection = sqlConnect();
            String statement =
            // INSERT INTO users (UserID, WINS, LOSSES, WINS_STREAK, LOSSES_STREAK) VALUES (12, 11, 10, 9, 8);
            "INSERT INTO " +
            Locations.Network.USER_TABLE.get() +
            " (UserID, WINS, LOSSES, WINS_STREAK, LOSSES_STREAK) VALUES ("+
            user.id.toString()          +", "+
            user.totalWins.toString()   +", "+
            user.totalLosses.toString() +", "+
            user.streakWins.toString()  +", "+
            user.streakLosses.toString()+");";

            connection.prepareStatement( statement ).execute();
            connection.close();
            App.LOGGER.info("Successfully put " + user.id + " into the database.");
        } catch (SQLException e) {
            App.LOGGER.error("Failed to put " + user.id + " into the database. \nThis data may be lost.");
            
            e.printStackTrace();
        }
    }

    /**
     * Get a user's data based on their ID string
     * @param id The user's ID
    **/
     public static JamerUser getUser(String id) {
        App.LOGGER.info("Getting " + id + " from the database.");
        JamerUser ju = null;
        
        try {
            Connection connection = sqlConnect();
            var rs = connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE UserID=%s;",
                Locations.Network.USER_TABLE.get(),
                id)
            ).executeQuery();

            if (rs.next()) {
                ju = new JamerUser(
                    rs.getString(1),
                    rs.getInt(2),
                    rs.getInt(3), 
                    rs.getInt(4), 
                    rs.getInt(5)
                );
                App.LOGGER.info("Successfully got " + id + " from the database.");            
            }
            rs.close();
            connection.close();
            
        } catch (SQLException e) {
            App.LOGGER.warn("Failed to get " + id + " from the database.");
            e.printStackTrace();
        }

        // it wasn't found in the database, create a new user
        if (ju == null) {
            ju = newUser(id);
        }

        return ju;
    }

    /**
     * Creates a new blank user, with all values zeroed, and id set to the passed in id
     * @param id of new user
     * @return New user profile
     */
    private static JamerUser newUser(String id) {
        // blank new user, incase it can't be gotten from the database.
        App.LOGGER.info("Creating new data for " + id);
        JamerUser ju = new JamerUser(id, 0, 0, 0, 0);
        put(ju); // save the new guy
        return ju;
    }

    /**
     * Send SQL commands via strings. The strings will automatically 
     * append " " between each arg so you don't need to add your own.
     * @param sql SQL commands
     * @return Result (if any) from you provided commands.
     * @throws SQLException
     */
    private static Connection sqlConnect() throws SQLException {
        App.LOGGER.info("Starting connection to database.");
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Locations.Network.DEFAULT_DB_URL.get(), "root", "123456");
            App.LOGGER.info("Connected to database.");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            App.LOGGER.error("Failed to connect to database. \nDo you have the dependency installed?");
        } // This would mean you don't have the mysql dependency installed.
        return connection;
    }



}
