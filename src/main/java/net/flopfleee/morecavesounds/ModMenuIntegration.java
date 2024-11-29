package net.flopfleee.morecavesounds;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static net.flopfleee.morecavesounds.MoreCaveSounds.LOGGER;

public class ModMenuIntegration {

    private MoreCaveSoundsConfig config = MoreCaveSoundsConfig.load();

    public Screen create(Screen parent) {
        if (config == null) {
            LOGGER.error("Config is null! Attempting to create default config.");
            config = new MoreCaveSoundsConfig(); // Fallback to default config
        }

        try {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("title.morecavesounds.config"));

            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.morecavesounds.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // Min Interval
            IntegerListEntry minIntervalEntry = entryBuilder.startIntField(
                            Text.translatable("option.morecavesounds.minIntervalSeconds"),
                            config.minIntervalSeconds)
                    .setDefaultValue(300)
                    .setTooltip(Text.translatable("tooltip.morecavesounds.minIntervalSeconds"))
                    .setSaveConsumer(newValue -> config.minIntervalSeconds = newValue)
                    .build();
            general.addEntry(minIntervalEntry);

            // Max Interval
            IntegerListEntry maxIntervalEntry = entryBuilder.startIntField(
                            Text.translatable("option.morecavesounds.maxIntervalSeconds"),
                            config.maxIntervalSeconds)
                    .setDefaultValue(1200)
                    .setTooltip(Text.translatable("tooltip.morecavesounds.maxIntervalSeconds"))
                    .setSaveConsumer(newValue -> config.maxIntervalSeconds = newValue)
                    .build();
            general.addEntry(maxIntervalEntry);

            // Sound Volume
            FloatListEntry volumeEntry = entryBuilder.startFloatField(
                            Text.translatable("option.morecavesounds.soundVolume"),
                            config.soundVolume)
                    .setDefaultValue(0.5f)
                    .setTooltip(Text.translatable("tooltip.morecavesounds.soundVolume"))
                    .setSaveConsumer(newValue -> config.soundVolume = newValue)
                    .build();
            general.addEntry(volumeEntry);

            builder.setSavingRunnable(config::save);
            return builder.build();
        } catch (Exception e) {
            LOGGER.error("Error creating config screen!", e);
            return parent;
        }
    }
}
