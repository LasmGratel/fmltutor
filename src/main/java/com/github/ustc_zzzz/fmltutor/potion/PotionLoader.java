package com.github.ustc_zzzz.fmltutor.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PotionLoader
{
    public static Potion potionFallProtection;

    public PotionLoader(FMLPreInitializationEvent event)
    {
        if (PotionLoader.expandPotionTypes())
        {
            int i = ConfigLoader.potionFallProtectId;
            if (i > 0 && i < 256 && Potion.potionTypes[i] == null)
            {
                potionFallProtection = new PotionFallProtection();
            }
            else
            {
                event.getModLog().error(
                        "Duplicate or illegal potion id: {}, the registry of class '{}' will be skipped. ", i,
                        PotionFallProtection.class.getName());
            }
        }
        else
        {
            event.getModLog().error("Find error while resizing potionTypes, the registry of potions will be skipped. ");
        }
    }

    /**
     * @return true if it succeeded
     */
    public static boolean expandPotionTypes()
    {
        for (Field field : Potion.class.getDeclaredFields())
        {
            field.setAccessible(true);
            if (Potion.class.equals(field.getType().getComponentType()))
            {
                try
                {
                    Field fieldmodifiers = Field.class.getDeclaredField("modifiers");
                    fieldmodifiers.setAccessible(true);
                    fieldmodifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    Potion[] potionTypes = (Potion[]) field.get(null);
                    field.set(null, Arrays.copyOf(potionTypes, 256));
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        return false;
    }
}
