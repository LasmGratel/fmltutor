package com.github.ustc_zzzz.fmltutor.enchantment;

import net.minecraft.enchantment.Enchantment;

import org.apache.logging.log4j.LogManager;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;

public class EnchantmentLoader
{
    public static Enchantment fireBurn;

    public EnchantmentLoader()
    {
        try
        {
            EnchantmentLoader.fireBurn = new EnchantmentFireBurn();
            Enchantment.addToBookList(EnchantmentLoader.fireBurn);
        }
        catch (Exception e)
        {
            LogManager.getLogger(FMLTutor.MODID).error(
                    "Duplicate or illegal enchantment id: {}, the registry of class '{}' will be skipped. ",
                    ConfigLoader.enchantmentFireBurnId, EnchantmentFireBurn.class.getName());
        }
    }
}
