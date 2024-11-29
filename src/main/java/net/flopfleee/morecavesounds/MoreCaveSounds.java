package net.flopfleee.morecavesounds;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class MoreCaveSounds implements ModInitializer {
	public static final String MOD_ID = "morecavesounds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final int TICKS_PER_SECOND = 20;
	private int tickCounter = 0;
	private int nextInterval = 0;

	public static MoreCaveSoundsConfig config;

	@Override
	public void onInitialize() {
		// Load the config
		config = MoreCaveSoundsConfig.load();
		setNextInterval();

		// Register the server tick event
		ServerTickEvents.START_SERVER_TICK.register(this::onServerTick);
		LOGGER.info("More Cave Sounds initialized!");
	}

	private void setNextInterval() {
		nextInterval = ThreadLocalRandom.current()
				.nextInt(
						config.minIntervalSeconds * TICKS_PER_SECOND,
						config.maxIntervalSeconds * TICKS_PER_SECOND
				);
	}

	private void onServerTick(MinecraftServer server) {
		tickCounter++;

		if (tickCounter >= nextInterval) {
			tickCounter = 0;
			setNextInterval();
			playCaveSound(server);
		}
	}

	private void playCaveSound(MinecraftServer server) {
		SoundEvent ambientCaveSound = Registries.SOUND_EVENT.get(SoundEvents.AMBIENT_CAVE.registryKey());

		for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			player.playSound(
					ambientCaveSound,
					SoundCategory.AMBIENT,
					config.soundVolume,
					1.0F
			);
		}
	}
}