package com.github.ustc_zzzz.fmltutor.common;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventLoader
{
    public EventLoader()
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
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
}
