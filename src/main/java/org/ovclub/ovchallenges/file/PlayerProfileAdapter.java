package org.ovclub.ovchallenges.file;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.ovclub.ovchallenges.object.PlayerProfile;
import org.ovclub.ovchallenges.object.events.ChallengeDTO;
import org.ovclub.ovchallenges.object.events.EventDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class PlayerProfileAdapter extends TypeAdapter<PlayerProfile> {
    private final Gson localGson;

    public PlayerProfileAdapter() {
        this.localGson = new Gson();
    }

    @Override
    public void write(JsonWriter out, PlayerProfile profile) throws IOException {
        out.beginObject();
        writeComplexObject(out, "events", profile.getEvents());
        writeComplexObject(out, "challenges", profile.getChallenges());
        out.endObject();
    }

    @Override
    public PlayerProfile read(JsonReader in) {
        return null;
    }

    private void writeComplexObject(JsonWriter out, String name, Object obj) throws IOException {
        out.name(name);
        if (obj instanceof List<?>) {
            out.beginArray();
            for (Object item : (List<?>) obj) {
                if (item instanceof EventDTO) {
                    writeEvent(out, (EventDTO) item);
                } else if (item instanceof ChallengeDTO) {
                    writeChallenge(out, (ChallengeDTO) item);
                } else {
                    JsonElement jsonElement = localGson.toJsonTree(item);
                    writeJsonElement(out, jsonElement);
                }
            }
            out.endArray();
        } else {
            out.nullValue();
        }
    }

    private void writeJsonElement(JsonWriter out, JsonElement element) throws IOException {
        if (element.isJsonNull()) {
            out.nullValue();
        } else if (element.isJsonObject()) {
            out.beginObject();
            for (Map.Entry<String, JsonElement> e : element.getAsJsonObject().entrySet()) {
                out.name(e.getKey());
                writeJsonElement(out, e.getValue());
            }
            out.endObject();
        } else if (element.isJsonArray()) {
            out.beginArray();
            for (JsonElement e : element.getAsJsonArray()) {
                writeJsonElement(out, e);
            }
            out.endArray();
        } else if (element instanceof JsonPrimitive){
            JsonPrimitive prim = element.getAsJsonPrimitive();
            if (prim.isBoolean()) {
                out.value(prim.getAsBoolean());
            } else if (prim.isString()) {
                out.value(prim.getAsString());
            } else if (prim.isNumber()) {
                out.value(prim.getAsNumber());
            }
        }
    }

    private void writeEvent(JsonWriter out, EventDTO event) throws IOException {
        out.beginObject();
        out.name(event.getName()).beginObject();

        for (Map.Entry<String, Integer> entry : event.getResults().entrySet()) {
            out.name(entry.getKey()).value(entry.getValue());
        }

        out.endObject();
        out.endObject();
    }

    private void writeChallenge(JsonWriter out, ChallengeDTO challenge) throws IOException {
        out.name(challenge.getName()).beginObject();
        out.name("wins").value(challenge.getWins());
        out.name("high-score").value(challenge.getHighScore());
        out.endObject();
    }
}
