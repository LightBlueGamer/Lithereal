package org.lithereal.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.lithereal.Lithereal;
import org.lithereal.block.*;

import static net.neoforged.neoforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Lithereal.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CREATIVE_ETHER_SOURCE);
        blockWithItem(ModBlocks.PASSIVE_ETHER_ABSORBER);
        blockWithItem(ModBlocks.ETHEREAL_CRYSTAL_BLOCK);

        blockWithItem(ModTreeBlocks.PHANTOM_OAK_PLANKS);
        plantWithItem(ModTreeBlocks.PHANTOM_OAK_SAPLING.get(), ModTreeBlocks.POTTED_PHANTOM_OAK_SAPLING.get(), TintState.NOT_TINTED);
        logBlock((RotatedPillarBlock) ModTreeBlocks.PHANTOM_OAK_LOG.get());
        logBlock((RotatedPillarBlock) ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        woodBlock((RotatedPillarBlock) ModTreeBlocks.PHANTOM_OAK_WOOD.get(), Lithereal.id("block/phantom_oak_log"));
        woodBlock((RotatedPillarBlock) ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get(), Lithereal.id("block/stripped_phantom_oak_log"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_LOG.get());
        itemForBlockModel(ModTreeBlocks.STRIPPED_PHANTOM_OAK_LOG.get());
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_WOOD.get());
        itemForBlockModel(ModTreeBlocks.STRIPPED_PHANTOM_OAK_WOOD.get());
        stairsBlock((StairBlock) ModTreeBlocks.PHANTOM_OAK_STAIRS.get(), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_STAIRS.get());
        signBlock((StandingSignBlock) ModTreeBlocks.PHANTOM_OAK_SIGN.get(), (WallSignBlock) ModTreeBlocks.PHANTOM_OAK_WALL_SIGN.get(), Lithereal.id("block/phantom_oak_planks"));
        hangingSignBlock((CeilingHangingSignBlock) ModTreeBlocks.PHANTOM_OAK_HANGING_SIGN.get(), (WallHangingSignBlock) ModTreeBlocks.PHANTOM_OAK_WALL_HANGING_SIGN.get(), Lithereal.id("block/stripped_phantom_oak_log"));
        pressurePlateBlock((PressurePlateBlock) ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get(), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_PRESSURE_PLATE.get());
        fenceWithItem((FenceBlock) ModTreeBlocks.PHANTOM_OAK_FENCE.get(), Lithereal.id("block/phantom_oak_planks"));
        trapdoorWithItem((TrapDoorBlock) ModTreeBlocks.PHANTOM_OAK_TRAPDOOR.get(), Lithereal.id("block/phantom_oak_trapdoor"), true);
        fenceGateBlock((FenceGateBlock) ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get(), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_FENCE_GATE.get());
        buttonWithItem((ButtonBlock) ModTreeBlocks.PHANTOM_OAK_BUTTON.get(), Lithereal.id("block/phantom_oak_planks"));
        slabBlock((SlabBlock) ModTreeBlocks.PHANTOM_OAK_SLAB.get(), Lithereal.id("block/phantom_oak_planks"), Lithereal.id("block/phantom_oak_planks"));
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_SLAB.get());
        doorBlock((DoorBlock) ModTreeBlocks.PHANTOM_OAK_DOOR.get(), Lithereal.id("block/phantom_oak_door_bottom"), Lithereal.id("block/phantom_oak_door_top"));
        leavesBlock(ModTreeBlocks.PHANTOM_OAK_LEAVES.get());
        itemForBlockModel(ModTreeBlocks.PHANTOM_OAK_LEAVES.get());

        plantWithItem(ModVegetationBlocks.MALISHROOM.get(), ModVegetationBlocks.POTTED_MALISHROOM.get(), TintState.NOT_TINTED);
        plantWithItem(ModVegetationBlocks.FORTSHROOM.get(), ModVegetationBlocks.POTTED_FORTSHROOM.get(), TintState.NOT_TINTED);

        blockWithItem(ModStoneBlocks.PAILITE);
        stairsBlock((StairBlock) ModStoneBlocks.PAILITE_STAIRS.get(), Lithereal.id("block/pailite"));
        itemForBlockModel(ModStoneBlocks.PAILITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.PAILITE_SLAB.get(), Lithereal.id("block/pailite"), Lithereal.id("block/pailite"));
        itemForBlockModel(ModStoneBlocks.PAILITE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.PAILITE_WALL.get(), Lithereal.id("block/pailite"));
        blockWithItem(ModStoneBlocks.POLISHED_PAILITE);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), Lithereal.id("block/polished_pailite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), Lithereal.id("block/polished_pailite"), Lithereal.id("block/polished_pailite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_PAILITE_WALL.get(), Lithereal.id("block/polished_pailite"));

        plantWithItem(ModPhantomBlocks.PHANTOM_ROSE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE.get(), TintState.NOT_TINTED);
        plantWithItem(ModPhantomBlocks.PHANTOM_ICE_FLOWER.get(), ModPhantomBlocks.POTTED_PHANTOM_ICE_FLOWER.get(), TintState.NOT_TINTED);
        plantWithItem(ModPhantomBlocks.PHANTOM_ROSE_ETHEREAL_CORE.get(), ModPhantomBlocks.POTTED_PHANTOM_ROSE_ETHEREAL_CORE.get(), TintState.NOT_TINTED);
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
        woodBlock(block, blockTexture(block));
    }

    private void woodBlock(RotatedPillarBlock block, ResourceLocation texture) {
        axisBlock(block, texture, texture);
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

    private void plant(Block normal, Block potted, TintState tintState) {
        plant(normal, potted, tintState, modLoc("block/" + name(normal)));
    }

    private void plant(Block normal, Block potted, TintState tintState, ResourceLocation texture) {
        simpleBlock(normal, models().singleTexture(name(normal), ResourceLocation.parse(tintState.getCross()), tintState.getCrossTexture(), texture));
        simpleBlock(potted, models().singleTexture(name(potted), ResourceLocation.parse(tintState.getCrossPot()), tintState.getPotTexture(), texture));
    }

    private void plantWithItem(Block normal, Block potted, TintState tintState) {
        ResourceLocation texture = modLoc("block/" + name(normal));
        this.plant(normal, potted, tintState, texture);
        itemModels().withExistingParent(modLoc(name(normal)).withPrefix("item/").toString(), "item/generated").texture("layer0", texture);
    }

    enum TintState {
        TINTED,
        NOT_TINTED;

        public String getCross() {
            return BLOCK_FOLDER + (this == TINTED ? "/tinted_cross" : "/cross");
        }

        public String getCrossPot() {
            return BLOCK_FOLDER + (this == TINTED ? "/tinted_flower_pot_cross" : "/flower_pot_cross");
        }

        public String getCrossTexture() {
            return "cross";
        }

        public String getPotTexture() {
            return "plant";
        }
    }
}