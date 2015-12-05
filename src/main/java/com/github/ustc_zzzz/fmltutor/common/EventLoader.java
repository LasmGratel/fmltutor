package com.github.ustc_zzzz.fmltutor.common;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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
