package com.cursee.stackable_stew_and_soup;

import com.cursee.monolib.util.toml.Toml;
import com.cursee.monolib.util.toml.TomlWriter;
import com.cursee.stackable_stew_and_soup.platform.Services;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MixinConfigForge implements IMixinConfigPlugin {

    static {
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            Constants.LOG.info("MixinConfigForge static initialization occurring!");
            Constants.LOG.info(" -  with getCanonicalName() {}", MixinConfigForge.class.getCanonicalName());
            Constants.LOG.info(" -  with descriptorString() {}", MixinConfigForge.class.descriptorString());
            Constants.LOG.info(" -  with getName() {}", MixinConfigForge.class.getName());
            Constants.LOG.info(" -  with getPackageName() {}", MixinConfigForge.class.getPackageName());
            Constants.LOG.info(" -  with getTypeName() {}", MixinConfigForge.class.getTypeName());
            Constants.LOG.info(" -  with getSimpleName() {}", MixinConfigForge.class.getSimpleName());
        }
    }

    public static long STACKABLE_AMOUNT = 8L;
    public static final Map<String, Object> defaults = new HashMap<String, Object>();

    @Override
    public void onLoad(String mixinPackage) {

        defaults.put("stackable_amount", STACKABLE_AMOUNT);

        final File CONFIG_DIRECTORY = new File(com.cursee.monolib.platform.Services.PLATFORM.getGameDirectory() + File.separator + "config");

        if (!CONFIG_DIRECTORY.isDirectory()) CONFIG_DIRECTORY.mkdir();

        final String CONFIG_FILEPATH = CONFIG_DIRECTORY + File.separator + Constants.MOD_ID + ".toml";

        File CONFIG_FILE = new File(CONFIG_FILEPATH);
        handle(CONFIG_FILE);
    }

    private static void handle(File file) {
        if (!file.isFile()) createConfigurationFile(file);
        else loadConfigurationFile(file);
    }

    private static void createConfigurationFile(File file) {
        try {
            TomlWriter writer = new TomlWriter();
            writer.write(defaults, file);
        }
        catch (IOException exception) {
            Constants.LOG.error("Fatal error occurred while attempting to write " + Constants.MOD_ID + ".toml");
            Constants.LOG.error("Did another process delete the config directory during writing?");
            Constants.LOG.error(exception.getMessage());
        }
    }

    private static void loadConfigurationFile(File file) {
        try {
            Toml toml = new Toml().read(file);
            STACKABLE_AMOUNT = toml.getLong("stackable_amount");
        }
        catch (IllegalStateException exception) {
            Constants.LOG.error("Fatal error occurred while attempting to read " + Constants.MOD_ID + ".toml");
            Constants.LOG.error("Did another process delete the file during reading?");
            Constants.LOG.error(exception.getMessage());
        }
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
