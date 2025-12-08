package org.lithereal.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;
import org.lithereal.block.ModStoneBlocks;
import org.lithereal.block.ModTreeBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Lithereal.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModTreeBlocks.PHANTOM_OAK_PLANKS);
        simpleBlock(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), models().cross("lithereal:phantom_oak_sapling", Lithereal.id("block/phantom_oak_sapling")));
        simpleBlockItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), itemModels().basicItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.getId()));
        logBlock((RotatedPillarBlock) ModTreeBlocks.PHANTOM_OAK_LOG.get());
        logBlock((RotatedPillarBlock) ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        woodBlock((RotatedPillarBlock) ModTreeBlocks.PHANTOM_OAK_WOOD.get());
        woodBlock((RotatedPillarBlock) ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get());
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_LOG.get());
        itemForBlockModel(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_WOOD.get());
        itemForBlockModel(ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get());
        stairsBlock((StairBlock) ModTreeBlocks.PHANTOM_OAK_STAIRS.get(), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_STAIRS.get());
        signBlock((StandingSignBlock) ModTreeBlocks.PHANTOM_OAK_SIGN.get(), (WallSignBlock) ModTreeBlocks.PHANTOM_OAK_WALL_SIGN, ResourceLocation.withDefaultNamespace("entity/signs/phantom_oak"));
        hangingSignBlock((CeilingHangingSignBlock) ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), (WallHangingSignBlock) ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN, ResourceLocation.withDefaultNamespace("entity/signs/hanging/phantom_oak"));
        pressurePlateBlock((PressurePlateBlock) ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get(), ResourceLocation.withDefaultNamespace("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get());
        fenceWithItem((FenceBlock) ModTreeBlocks.PHANTOM_OAK_FENCE.get(), Lithereal.id("block/phantom_oak_planks"));
        trapdoorWithItem((TrapDoorBlock) ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get(), Lithereal.id("block/phantom_oak_trapdoor"), true);
        fenceGateBlock((FenceGateBlock) ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get(), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get());
        buttonWithItem((ButtonBlock) ModTreeBlocks.PHANTOM_OAK_BUTTON.get(), Lithereal.id("block/phantom_oak_planks"));
        slabBlock((SlabBlock) ModTreeBlocks.PHANTOM_OAK_SLAB.get(), Lithereal.id("block/phantom_oak_planks"), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModelWithSuffix(ModTreeBlocks.PHANTOM_OAK_SLAB.get(), "_bottom");
        doorBlock((DoorBlock) ModTreeBlocks.PHANTOM_OAK_DOOR.get(), Lithereal.id("block/phantom_oak_door_bottom"), Lithereal.id("block/phantom_oak_door_top"));
        leavesBlock(ModTreeBlocks.PHANTOM_OAK_LEAVES.get());
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_LEAVES.get());

        blockWithItem(ModStoneBlocks.PAILITE);
        stairsBlock((StairBlock) ModStoneBlocks.PAILITE_STAIRS.get(), Lithereal.id("block/pailite"));
        itemForBlockModel(ModStoneBlocks.PAILITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.PAILITE_SLAB.get(), Lithereal.id("block/pailite"), Lithereal.id("block/pailite"));
        itemForBlockModelWithSuffix(ModStoneBlocks.PAILITE_SLAB.get(), "_bottom");
        wallWithItem((WallBlock) ModStoneBlocks.PAILITE_WALL.get(), Lithereal.id("block/pailite"));
        blockWithItem(ModStoneBlocks.POLISHED_PAILITE);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), Lithereal.id("block/polished_pailite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), Lithereal.id("block/polished_pailite"), Lithereal.id("block/polished_pailite"));
        itemForBlockModelWithSuffix(ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), "_bottom");
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_PAILITE_WALL.get(), Lithereal.id("block/polished_pailite"));
    }

    private void blockWithItem(RegistrySupplier<? extends Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void leavesBlock(Block block) {
        leavesBlock(block, blockTexture(block));
    }

    private void leavesBlock(Block block, ResourceLocation texture) {
        simpleBlock(block, models().leaves(name(block), texture));
    }

    private void woodBlock(RotatedPillarBlock block) {
        axisBlock(block, blockTexture(block), blockTexture(block));
    }

    private void itemForBlockModel(Block block) {
        itemForBlockModel(block, modLoc("block/" + name(block)).toString());
    }

    private void itemForBlockModelWithSuffix(Block block, String suffix) {
        itemForBlockModel(block, modLoc("block/" + name(block) + suffix).toString());
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private void itemForBlockModel(Block block, String parent) {
        itemModels().withExistingParent(name(block), parent);
    }

    private void fenceWithItem(FenceBlock fenceBlock, ResourceLocation texture) {
        fenceBlock(fenceBlock, texture);
        simpleBlockItem(fenceBlock, models().fenceInventory(name(fenceBlock) + "_inventory", texture));
    }

    private void wallWithItem(WallBlock wallBlock, ResourceLocation texture) {
        wallBlock(wallBlock, texture);
        simpleBlockItem(wallBlock, models().wallInventory(name(wallBlock) + "_inventory", texture));
    }

    private void trapdoorWithItem(TrapDoorBlock trapDoorBlock, ResourceLocation texture, boolean orientable) {
        trapdoorBlock(trapDoorBlock, texture, orientable);
        itemForBlockModelWithSuffix(trapDoorBlock, "_bottom");
    }

    private void buttonWithItem(ButtonBlock buttonBlock, ResourceLocation texture) {
        buttonBlock(buttonBlock, texture);
        simpleBlockItem(buttonBlock, models().buttonInventory(name(buttonBlock) + "_inventory", texture));
    }
}