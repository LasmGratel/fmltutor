package com.github.ustc_zzzz.fmltutor.block;

import com.github.ustc_zzzz.fmltutor.common.EventLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockGrassBlock extends Block
{
    public BlockGrassBlock()
    {
        super(Material.ground);
        this.setUnlocalizedName("grassBlock");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        EventLoader.PlayerClickGrassBlockEvent event;
        EventLoader.EVENT_BUS.post(event = new EventLoader.PlayerClickGrassBlockEvent(playerIn, pos, worldIn));
        if (!event.isCanceled() && !worldIn.isRemote)
        {
            worldIn.setBlockToAir(pos);
        }
    }
}
