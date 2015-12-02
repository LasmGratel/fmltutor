package com.github.ustc_zzzz.fmltutor.item;

import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;

import net.minecraft.item.ItemArmor;

public class ItemRedstoneArmor extends ItemArmor
{
    public ItemRedstoneArmor(int armorType)
    {
        super(ItemLoader.REDSTONE_ARMOR, ItemLoader.REDSTONE_ARMOR.ordinal(), armorType);
    }

    public static class Helmet extends ItemRedstoneArmor
    {
        public Helmet()
        {
            super(0);
            this.setUnlocalizedName("redstoneHelmet");
            this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        }
    }

    public static class Chestplate extends ItemRedstoneArmor
    {
        public Chestplate()
        {
            super(1);
            this.setUnlocalizedName("redstoneChestplate");
            this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        }
    }

    public static class Leggings extends ItemRedstoneArmor
    {
        public Leggings()
        {
            super(2);
            this.setUnlocalizedName("redstoneLeggings");
            this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        }
    }

    public static class Boots extends ItemRedstoneArmor
    {
        public Boots()
        {
            super(3);
            this.setUnlocalizedName("redstoneBoots");
            this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        }
    }
}
