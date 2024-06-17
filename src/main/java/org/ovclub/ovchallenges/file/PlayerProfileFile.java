//package org.ovclub.ovchallenges.file;
//
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;
//import org.ovclub.ovchallenges.Plugin;
//import org.ovclub.ovchallenges.object.PlayerProfile;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Map;
//
//public class PlayerProfileFile {
//    private final Plugin plugin;
//    private File playerProfileFile;
//
//    public PlayerProfileFile(final Plugin plugin){
//        this.plugin = plugin;
//    }
//
//    public void loadCrews() {
//        Gson gson = new Gson();
//        playerProfileFile = new File(plugin.getDataFolder(), "player_profiles.json");
//        if (!playerProfileFile.exists()) {
//            try {
//                playerProfileFile.getParentFile().mkdirs();
//                playerProfileFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//        }
//        try (FileReader reader = new FileReader(playerProfileFile)) {
//            JsonObject profileData;
//            try {
//                profileData = JsonParser.parseReader(reader).getAsJsonObject();
//            } catch (JsonSyntaxException e) {
//                System.err.println("Error parsing JSON: " + e.getMessage());
//                return;
//            }
//            if (profileData == null) {
//                System.err.println("No data found in player_profiles.json");
//                return;
//            }
//            for (Map.Entry<String, JsonElement> entry : profileData.entrySet()) {
//                String uuid = entry.getKey();
//                Type type = new TypeToken<Map<String, Object>>() {}.getType();
//                Map<String, Object> crewMap = gson.fromJson(entry.getValue(), type);
//                PlayerProfile loadedProfile = PlayerProfile.deserialize(uuid, crewMap, plugin);
//                plugin.getData().addCrew(loadedProfile);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
