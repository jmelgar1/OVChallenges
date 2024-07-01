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
        if (obj instanceof List<?> list) {
            out.beginObject();
            for (Object item : list) {
                if (item instanceof EventDTO event) {
                    out.name(event.getName());
                    writeEvent(out, event);
                } else if (item instanceof ChallengeDTO challenge) {
                    out.name(challenge.getName());
                    writeChallenge(out, challenge);
                }
            }
            out.endObject();
        } else {
            out.nullValue();
        }
    }

    private void writeEvent(JsonWriter out, EventDTO event) throws IOException {
        out.beginObject();
        System.out.println(event.getResults());
        for (Map.Entry<String, Integer> entry : event.getResults().entrySet()) {
            out.name(entry.getKey()).value(entry.getValue());
        }
        out.endObject();
    }

    private void writeChallenge(JsonWriter out, ChallengeDTO challenge) throws IOException {
        out.beginObject();
        out.name("wins").value(challenge.getWins());
        out.name("high-score").value(challenge.getHighScore());
        out.endObject();
    }
}
