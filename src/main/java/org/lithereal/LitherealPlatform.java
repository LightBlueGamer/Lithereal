package org.lithereal;


//? fabric  {
import org.lithereal.fabric.FabricLitherealPlatformImpl;
//?}
//? neoforge {
/*import org.lithereal.neoforge.NeoForgeLitherealPlatformImpl;
 *///?}
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.nio.file.Path;
import java.util.function.Supplier;

public interface LitherealPlatform {

    //? fabric  {
    LitherealPlatform INSTANCE = new FabricLitherealPlatformImpl();
    //?}
    //? neoforge {
    /*LitherealPlatform INSTANCE = new NeoForgeLitherealPlatformImpl();
    *///?}

    WoodType registerWoodType(WoodType woodType);
    
    RotatedPillarBlock strippableLog(Supplier<Block> stripped, BlockBehaviour.Properties properties);
    
    Path getConfigDirectory();
}