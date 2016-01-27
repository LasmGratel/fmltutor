package com.github.ustc_zzzz.fmltutor.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandPosition extends CommandBase
{
    @Override
    public String getName()
    {
        return "position";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.position.usage";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 1)
        {
            throw new WrongUsageException("commands.position.usage", new Object[0]);
        }
        else
        {
            EntityPlayerMP entityPlayerMP = args.length > 0 ? CommandBase.getPlayer(sender, args[0])
                    : CommandBase.getCommandSenderAsPlayer(sender);
            sender.addChatMessage(new ChatComponentTranslation("commands.position.success", entityPlayerMP.getName(),
                    entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ,
                    entityPlayerMP.worldObj.provider.getDimensionName()));
        }
    }

    @Override
    public List<?> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            String[] names = MinecraftServer.getServer().getAllUsernames();
            return CommandBase.getListOfStringsMatchingLastWord(args, names);
        }
        return null;
    }
}
