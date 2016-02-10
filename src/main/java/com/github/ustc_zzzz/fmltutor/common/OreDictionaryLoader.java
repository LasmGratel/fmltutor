package com.github.ustc_zzzz.fmltutor.common;

import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader
{
    public OreDictionaryLoader()
    {
        OreDictionary.registerOre("dustRedstone", Items.glowstone_dust);
        OreDictionary.registerOre("dustGlowstone", Items.redstone);
    }
}
