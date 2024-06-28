package org.ovclub.ovchallenges.file;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.ovclub.ovchallenges.Plugin;
import org.ovclub.ovchallenges.object.PlayerProfile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class PlayerProfileFile {
    private final Plugin plugin;
    private File playerProfileFile;

    public PlayerProfileFile(final Plugin plugin){
        this.plugin = plugin;
    }

    public void loadProfiles() {
        Gson gson = new Gson();
        playerProfileFile = new File(plugin.getDataFolder(), "player_profiles.json");
        if (!playerProfileFile.exists()) {
            try {
                playerProfileFile.getParentFile().mkdirs();
                playerProfileFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try (FileReader reader = new FileReader(playerProfileFile)) {
            JsonObject profileData;
            try {
                profileData = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (JsonSyntaxException e) {
                System.err.println("Error parsing JSON: " + e.getMessage());
                return;
            }
            if (profileData == null) {
                System.err.println("No data found in player_profiles.json");
                return;
            }
            for (Map.Entry<String, JsonElement> entry : profileData.entrySet()) {
                String uuid = entry.getKey();
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                Map<String, Object> profileMap = gson.fromJson(entry.getValue(), type);
                PlayerProfile loadedProfile = PlayerProfile.deserialize(uuid, profileMap, plugin);
                plugin.getData().addPlayerProfile(loadedProfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProfiles() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlayerProfile.class, new PlayerProfileAdapter())
                .create();

        JsonObject allProfilesJson = new JsonObject();
        for (PlayerProfile profile : plugin.getData().getPlayerProfiles()) {
            JsonObject profileJson = gson.toJsonTree(profile).getAsJsonObject();
            if(profile.getUuid() == null) {
                profile.setUuid();
            }
            allProfilesJson.add(profile.getUuid(), profileJson);
        }
        try (FileWriter file = new FileWriter(playerProfileFile)) {
            gson.toJson(allProfilesJson, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
