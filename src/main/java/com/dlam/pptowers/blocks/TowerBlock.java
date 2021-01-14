package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.ArrowTowerBlockEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TowerBlock extends Block implements BlockEntityProvider {
    public TowerBlock(Settings settings) {
        super(settings);
    }

    //Will remove later. Using for debugging
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ArrowTowerBlockEntity arrTowEnt = (ArrowTowerBlockEntity) world.getBlockEntity(pos);
            player.sendMessage(new LiteralText("Target: " + arrTowEnt.getHostileEntities()), false);
            player.sendMessage(new LiteralText("Target: " + arrTowEnt.target), false);
        }

        return ActionResult.SUCCESS;
    }
}
