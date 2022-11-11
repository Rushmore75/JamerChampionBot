package discordbot.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import discordbot.Locations;

public class NeoDatabase {
    // TODO consolidate methods

    public static void put(JamerUser user) {
        try {
            Connection connection = sqlConnect();
            String statement =
            // INSERT INTO  users (UserID, WINS, LOSSES, WINS_STREAK, LOSSES_STREAK) VALUES (12, 11, 10, 9, 8);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JamerUser getUser(String id) {
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
            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
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
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Locations.Network.DEFAULT_DB_URL.get(), "root", "123456");

            
        } catch (ClassNotFoundException e) { e.printStackTrace(); } // This would mean you don't have the mysql dependency installed.
        return connection;
    }



}
