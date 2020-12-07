package com.dlam.pptowers;

import com.dlam.pptowers.registry.BlockEntities;
import com.dlam.pptowers.registry.Blocks;
import com.dlam.pptowers.registry.Items;
import net.fabricmc.api.ModInitializer;

public class PPTowers implements ModInitializer {
    public static final String MOD_ID = "pptowers";

    @Override
    public void onInitialize() {
        Items.registerItems();
        Blocks.registerBlocks();
        BlockEntities.registerBlockEntities();
    }
}
