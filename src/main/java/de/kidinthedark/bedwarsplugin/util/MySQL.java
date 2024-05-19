package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("SqlSourceToSinkFlow")
public class MySQL {

    private final String HOST;
    private final String PORT;
    private final String DATABASE;
    private final String USER;
    private final String PASSWORD;
    private final boolean useSSL;
    private final boolean autoReconnect;
    private final boolean allowPublicKeyRetrieval;
    private java.sql.Connection con;
    private final String pre = "[MySQL] ";

    public MySQL(String host, String port, String database, String user, String password, boolean ssl,
                 boolean autoReconnect, boolean allowPublicKeyRetrieval) {
        this.HOST = host;
        this.PORT = port;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        this.useSSL = ssl;
        this.allowPublicKeyRetrieval = allowPublicKeyRetrieval;
        this.autoReconnect = autoReconnect;
    }

    public void connect() {
        if(isConnected()) return;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=" + useSSL
                            + "&autoReconnect=" + autoReconnect + "&allowPublicKeyRetrieval=" + allowPublicKeyRetrieval, USER,
                    PASSWORD);
        } catch (SQLException e) {
            BedwarsPlugin.instance.getLogger().severe(pre + "MySQL Verbindung fehlgeschlagen: " + e.getMessage());
            return;
        }
        BedwarsPlugin.instance.getLogger().info(pre + "MySQL Verbindung erfolgreich aufgebaut!");

    }

    public boolean isConnected() {
        return con != null;
    }

    public void disconnect() {
        if(!isConnected()) return;
        try {
            con.close();
        } catch (SQLException e) {
            BedwarsPlugin.instance.getLogger().severe(pre + "In der MySQL Verbindung ist ein Fehler aufgetreten: " + e.getMessage());
            return;
        }
        BedwarsPlugin.instance.getLogger().info(pre + "MySQL Verbindung erfolgreich abgebaut!");
    }

    public void update(String qry) {
        if(isConnected()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet getResult(String qry) {
        if(isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                BedwarsPlugin.instance.getLogger().severe(pre + "In der MySQL Verbindung ist ein Fehler aufgetreten: " + e.getMessage());
            }
        }
        return null;
    }

}

