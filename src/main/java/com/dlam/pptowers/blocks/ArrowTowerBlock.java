package com.dlam.pptowers.blocks;

import com.dlam.pptowers.entities.ArrowTowerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class ArrowTowerBlock extends TowerBlock {
    public ArrowTowerBlock(Settings settings) {
        super(settings);
    }

    //Will remove later. Using for debugging
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ArrowTowerBlockEntity arrTowEnt = (ArrowTowerBlockEntity) world.getBlockEntity(pos);
            List<Entity> ents = arrTowEnt.getHostileEntities();
            player.sendMessage(new LiteralText("List: " + ents), false);
            player.sendMessage(new LiteralText("Range: " + arrTowEnt.range), false);
            player.sendMessage(new LiteralText("Pos: " + arrTowEnt.getPos()), false);
            ents.forEach(ent -> player.sendMessage(new LiteralText("Ent: " + ent), false));

        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ArrowTowerBlockEntity();
    }

}
