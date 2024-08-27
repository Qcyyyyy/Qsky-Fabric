package qcy.qsky;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QskyConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/qskyconfig.json");

    public String someSetting = "default";
    public int islandInfoX = 0;
    public int islandInfoY = 0;
    public int entityHudX = 0;
    public int entityHudY = 0;
    public int commandCooldownX = 0;
    public int commandCooldownY = 0;
    public int rank = -1;

    // Load the config from the file, or create a new one if it doesn't exist
    public static QskyConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, QskyConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new QskyConfig(); // Return a default config if loading fails
    }

    // Save the config to the file
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
