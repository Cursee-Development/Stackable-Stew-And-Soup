package com.cursee.stackable_stew_and_soup.mixin;

import com.cursee.stackable_stew_and_soup.Constants;
import com.cursee.stackable_stew_and_soup.platform.Services;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ForgeTitleScreenMixin {

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            Constants.LOG.info("Minecraft TitleScreen initialized in a {} development environment!", Services.PLATFORM.getPlatformName());
        }
    }
}