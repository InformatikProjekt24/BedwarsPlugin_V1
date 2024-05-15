package de.kidinthedark.bhwmcplugin.util.mcapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.kidinthedark.bhwmcplugin.BHWMcPlugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIConnector {
    @SuppressWarnings("resource")
    public static APIMessage getPlayerId(String name) throws IOException {

        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);

        BHWMcPlugin.inst.getLogger().info("[Mojang API] Sende Anfrage an Mojang API: " + "users/profiles/minecraft/" + name);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try {
            connection.connect();
        } catch (Exception e) {
            return new APIMessage(400, null);
        }
        long start = System.currentTimeMillis();

        while(connection.getInputStream().available() == 0) {
            if(System.currentTimeMillis() - start > 5000) {
                BHWMcPlugin.inst.getLogger().warning("[Mojang API] Keine Antwort von der Mojang API, breche ab"  + " Status: " + connection.getResponseCode());
                return new APIMessage(408, null);
            }
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(connection.getInputStream());
        } catch (IOException e) {
            return new APIMessage(404, null);
        }

        String result = "";

        while (scanner.hasNext()) {
            result = result + scanner.nextLine();
        }

        BHWMcPlugin.inst.getLogger().info("[Mojang API] Antwort von Mojang API (" + (System.currentTimeMillis() - start) + "ms):" + result + " Status: " + connection.getResponseCode());

        JsonObject json = new Gson().fromJson(result, JsonObject.class);

        if(json.has("errorMessage")) {
            return new APIMessage(404, null);
        }

        return new APIMessage(200, json);
    }

    @SuppressWarnings("resource")
    public static APIMessage getPlayerName(String uuid) throws IOException {

        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);

        BHWMcPlugin.inst.getLogger().info("[Mojang API] Sende Anfrage an Mojang Sessionserver: " + "session/minecraft/profile/" + uuid);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try {
            connection.connect();
        } catch (Exception e) {
            return new APIMessage(400, null);
        }
        long start = System.currentTimeMillis();

        while (connection.getInputStream().available() == 0) {
            if (System.currentTimeMillis() - start > 5000) {
                BHWMcPlugin.inst.getLogger().warning("[Mojang API] Keine Antwort von der Mojang API, breche ab"  + " Status: " + connection.getResponseCode());
                return new APIMessage(408, null);
            }
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(connection.getInputStream());
        } catch (IOException e) {
            return new APIMessage(404, null);
        }

        String result = "";

        while (scanner.hasNext()) {
            result = result + scanner.nextLine();
        }

        BHWMcPlugin.inst.getLogger().info("[Mojang API] Antwort von Mojang API (" + (System.currentTimeMillis() - start) + "ms):" + result + " Status: " + connection.getResponseCode());

        JsonObject json = new Gson().fromJson(result, JsonObject.class);

        return new APIMessage(200, json);
    }
}
