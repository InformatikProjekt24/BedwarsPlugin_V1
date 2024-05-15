package de.kidinthedark.bhwmcplugin.util.mcapi;

import com.google.gson.JsonObject;

public class APIMessage {

    private int statuscode;
    private JsonObject message;

    public APIMessage(int statuscode, JsonObject message) {
        this.statuscode = statuscode;
        this.message = message;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public JsonObject getJson() {
        return message;
    }

    public String getValue(String name) {
        return message.get(name).toString();
    }




}
