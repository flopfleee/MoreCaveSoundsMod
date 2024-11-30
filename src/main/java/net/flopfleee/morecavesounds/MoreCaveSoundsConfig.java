package net.flopfleee.morecavesounds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MoreCaveSoundsConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "morecavesounds.json");

    public int minIntervalSeconds = 300;
    public int maxIntervalSeconds = 1200;
    public float soundVolume = 0.5f;

    public static MoreCaveSoundsConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, MoreCaveSoundsConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new MoreCaveSoundsConfig();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
