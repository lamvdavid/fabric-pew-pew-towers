package com.dlam.pptowers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;

public abstract class TowerBlock extends Block implements BlockEntityProvider {
    public TowerBlock(Settings settings) {
        super(settings);
    }

    /*Will remove later. Using for debugging
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ArrowTowerBlockEntity arrTowEnt = (ArrowTowerBlockEntity) world.getBlockEntity(pos);
            player.sendMessage(new LiteralText("Target: " + arrTowEnt.getHostileEntities()), false);
            player.sendMessage(new LiteralText("Target: " + arrTowEnt.target), false);
        }

        return ActionResult.SUCCESS;
    }

     */
}
