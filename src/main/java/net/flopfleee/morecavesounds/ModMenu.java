package net.flopfleee.morecavesounds;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;

public class ModMenu implements ModMenuApi, ClientModInitializer {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return new ModMenuIntegration()::create;
    }

    @Override
    public void onInitializeClient() {
        getModConfigScreenFactory();
    }
}
