package com.github.ustc_zzzz.fmltutor.common;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.ustc_zzzz.fmltutor.achievement.AchievementLoader;
import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.client.KeyLoader;
import com.github.ustc_zzzz.fmltutor.enchantment.EnchantmentLoader;
import com.github.ustc_zzzz.fmltutor.potion.PotionLoader;

public class EventLoader
{
    public static final EventBus EVENT_BUS = new EventBus();

    public EventLoader()
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        EventLoader.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntityPig)
        {
            EntityPig pig = (EntityPig) event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && (stack.getItem() == Items.wheat || stack.getItem() == Items.wheat_seeds))
            {
                player.attackEntityFrom((new DamageSource("byPig")).setDifficultyScaled().setExplosion(), 8.0F);
                player.worldObj.createExplosion(pig, pig.posX, pig.posY, pig.posZ, 2.0F, false);
                pig.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
        if (event.player.isServerWorld())
        {
            event.player.addChatComponentMessage(new ChatComponentText(event.pickedUp.toString()));
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.world.isRemote)
        {
            event.entityPlayer.addChatComponentMessage(new ChatComponentText(event.pos.toString()));
        }
    }

    @SubscribeEvent
    public void onPlayerClickGrassBlock(PlayerClickGrassBlockEvent event)
    {
        if (!event.world.isRemote && event.entityPlayer.getHeldItem() == null)
        {
            BlockPos pos = event.pos;
            event.world.spawnEntityInWorld(
                    new EntityTNTPrimed(event.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, null));
            event.entityPlayer.triggerAchievement(AchievementLoader.explosionFromGrassBlock);
        }
    }

    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null)
        {
            ItemStack itemStack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireBurn.effectId, itemStack) > 0
                    && itemStack.getItem() != Items.shears)
            {
                for (ItemStack stack : event.drops)
                {
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null)
                    {
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(event.drops.indexOf(stack), newStack);
                    }
                    else if (stack != null)
                    {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b && (block.isFlammable(event.world, event.pos, EnumFacing.DOWN)
                                || block.isFlammable(event.world, event.pos, EnumFacing.EAST)
                                || block.isFlammable(event.world, event.pos, EnumFacing.NORTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.SOUTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.UP)
                                || block.isFlammable(event.world, event.pos, EnumFacing.WEST)))
                        {
                            event.drops.remove(stack);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        if (event.source.getDamageType().equals("fall"))
        {
            PotionEffect effect = event.entityLiving.getActivePotionEffect(PotionLoader.potionFallProtection);
            if (effect != null)
            {
                if (effect.getAmplifier() == 0)
                {
                    event.ammount /= 2;
                }
                else
                {
                    event.ammount = 0;
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        if (event.entityLiving instanceof EntityPlayer && event.source.getDamageType().equals("byPig"))
        {
            ((EntityPlayer) event.entityLiving).triggerAchievement(AchievementLoader.worseThanPig);
        }
    }

    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
        event.player.worldObj.playSoundAtEntity(event.player, "fmltutor:fmltutor.test", 1.0F, 1.0F);
        if (event.crafting.getItem() == Item.getItemFromBlock(BlockLoader.grassBlock))
        {
            event.player.triggerAchievement(AchievementLoader.buildGrassBlock);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyLoader.showTime.isPressed())
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World world = Minecraft.getMinecraft().theWorld;
            player.addChatMessage(new ChatComponentTranslation("chat.fmltutor.time", world.getTotalWorldTime()));
        }

    }

    @Cancelable
    public static class PlayerClickGrassBlockEvent extends PlayerInteractEvent
    {
        public PlayerClickGrassBlockEvent(EntityPlayer player, BlockPos pos, World world)
        {
            super(player, PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, pos, null, world);
        }
    }
}
