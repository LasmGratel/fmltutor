package com.github.ustc_zzzz.fmltutor.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader
{
    private static Configuration config;

    public static int diamondBurnTime;

    public static int enchantmentFireBurnId;

    public static int potionFallProtectId;

    public ConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        registerConfig();

        config.save();
    }

    private static void registerConfig()
    {
        diamondBurnTime = config.get(Configuration.CATEGORY_GENERAL, "diamondBurnTime", 640).getInt();
        enchantmentFireBurnId = config.get(Configuration.CATEGORY_GENERAL, "enchantmentFireBurnId", 36).getInt();
        potionFallProtectId = config.get(Configuration.CATEGORY_GENERAL, "potionFallProtectId", 32).getInt();
    }
}
