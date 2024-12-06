package com.cursee.stackable_stew_and_soup;

import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;

public class StackableStewAndSoupFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        StackableStewAndSoup.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
    }
}
