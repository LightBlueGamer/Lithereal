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
        blockWithItem(ModBlocks.PURE_ETHER_SOURCE);
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

        blockWithItem(ModStoneBlocks.ETHERSTONE);
        stairsBlock((StairBlock) ModStoneBlocks.ETHERSTONE_STAIRS.get(), Lithereal.id("block/etherstone"));
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.ETHERSTONE_SLAB.get(), Lithereal.id("block/etherstone"), Lithereal.id("block/etherstone"));
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.ETHERSTONE_WALL.get(), Lithereal.id("block/etherstone"));
        cubeColumn(ModStoneBlocks.CHISELED_ETHERSTONE.get(), Lithereal.id("block/chiseled_etherstone_side"), Lithereal.id("block/chiseled_etherstone_top"));
        itemForBlockModel(ModStoneBlocks.CHISELED_ETHERSTONE.get());
        blockWithItem(ModStoneBlocks.POLISHED_ETHERSTONE);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get(), Lithereal.id("block/polished_etherstone"));
        itemForBlockModel(ModStoneBlocks.POLISHED_ETHERSTONE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get(), Lithereal.id("block/polished_etherstone"), Lithereal.id("block/polished_etherstone"));
        itemForBlockModel(ModStoneBlocks.POLISHED_ETHERSTONE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_ETHERSTONE_WALL.get(), Lithereal.id("block/polished_etherstone"));
        blockWithItem(ModStoneBlocks.ETHERSTONE_BRICKS);
        stairsBlock((StairBlock) ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get(), Lithereal.id("block/etherstone_bricks"));
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_BRICK_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get(), Lithereal.id("block/etherstone_bricks"), Lithereal.id("block/etherstone_bricks"));
        itemForBlockModel(ModStoneBlocks.ETHERSTONE_BRICK_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.ETHERSTONE_BRICK_WALL.get(), Lithereal.id("block/etherstone_bricks"));

        blockWithItem(ModStoneBlocks.PAILITE);
        stairsBlock((StairBlock) ModStoneBlocks.PAILITE_STAIRS.get(), Lithereal.id("block/pailite"));
        itemForBlockModel(ModStoneBlocks.PAILITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.PAILITE_SLAB.get(), Lithereal.id("block/pailite"), Lithereal.id("block/pailite"));
        itemForBlockModel(ModStoneBlocks.PAILITE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.PAILITE_WALL.get(), Lithereal.id("block/pailite"));
        buttonWithItem((ButtonBlock) ModStoneBlocks.PAILITE_BUTTON.get(), Lithereal.id("block/pailite"));
        pressurePlateBlock((PressurePlateBlock) ModStoneBlocks.PAILITE_PRESSURE_PLATE.get(), Lithereal.id("block/pailite"));
        itemForBlockModel(ModStoneBlocks.PAILITE_PRESSURE_PLATE.get());
        blockWithItem(ModStoneBlocks.POLISHED_PAILITE);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_PAILITE_STAIRS.get(), Lithereal.id("block/polished_pailite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_PAILITE_SLAB.get(), Lithereal.id("block/polished_pailite"), Lithereal.id("block/polished_pailite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_PAILITE_WALL.get(), Lithereal.id("block/polished_pailite"));
        buttonWithItem((ButtonBlock) ModStoneBlocks.POLISHED_PAILITE_BUTTON.get(), Lithereal.id("block/polished_pailite"));
        pressurePlateBlock((PressurePlateBlock) ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get(), Lithereal.id("block/polished_pailite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_PRESSURE_PLATE.get());
        cubeColumn(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get(), Lithereal.id("block/chiseled_polished_pailite_bricks"), Lithereal.id("block/chiseled_polished_pailite_bricks_top"));
        itemForBlockModel(ModStoneBlocks.CHISELED_POLISHED_PAILITE_BRICKS.get());
        blockWithItem(ModStoneBlocks.POLISHED_PAILITE_BRICKS);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get(), Lithereal.id("block/polished_pailite_bricks"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICK_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get(), Lithereal.id("block/polished_pailite_bricks"), Lithereal.id("block/polished_pailite_bricks"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICK_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_PAILITE_BRICK_WALL.get(), Lithereal.id("block/polished_pailite_bricks"));
        buttonWithItem((ButtonBlock) ModStoneBlocks.POLISHED_PAILITE_BRICK_BUTTON.get(), Lithereal.id("block/polished_pailite_bricks"));
        pressurePlateBlock((PressurePlateBlock) ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get(), Lithereal.id("block/polished_pailite_bricks"));
        itemForBlockModel(ModStoneBlocks.POLISHED_PAILITE_BRICK_PRESSURE_PLATE.get());

        blockWithItem(ModStoneBlocks.LUMINITE);
        stairsBlock((StairBlock) ModStoneBlocks.LUMINITE_STAIRS.get(), Lithereal.id("block/luminite"));
        itemForBlockModel(ModStoneBlocks.LUMINITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.LUMINITE_SLAB.get(), Lithereal.id("block/luminite"), Lithereal.id("block/luminite"));
        itemForBlockModel(ModStoneBlocks.LUMINITE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.LUMINITE_WALL.get(), Lithereal.id("block/luminite"));
        blockWithItem(ModStoneBlocks.POLISHED_LUMINITE);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get(), Lithereal.id("block/polished_luminite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_LUMINITE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_LUMINITE_SLAB.get(), Lithereal.id("block/polished_luminite"), Lithereal.id("block/polished_luminite"));
        itemForBlockModel(ModStoneBlocks.POLISHED_LUMINITE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_LUMINITE_WALL.get(), Lithereal.id("block/polished_luminite"));

        blockWithItem(ModStoneBlocks.VERDONE);
        stairsBlock((StairBlock) ModStoneBlocks.VERDONE_STAIRS.get(), Lithereal.id("block/verdone"));
        itemForBlockModel(ModStoneBlocks.VERDONE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.VERDONE_SLAB.get(), Lithereal.id("block/verdone"), Lithereal.id("block/verdone"));
        itemForBlockModel(ModStoneBlocks.VERDONE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.VERDONE_WALL.get(), Lithereal.id("block/verdone"));
        blockWithItem(ModStoneBlocks.POLISHED_VERDONE);
        stairsBlock((StairBlock) ModStoneBlocks.POLISHED_VERDONE_STAIRS.get(), Lithereal.id("block/polished_verdone"));
        itemForBlockModel(ModStoneBlocks.POLISHED_VERDONE_STAIRS.get());
        slabBlock((SlabBlock) ModStoneBlocks.POLISHED_VERDONE_SLAB.get(), Lithereal.id("block/polished_verdone"), Lithereal.id("block/polished_verdone"));
        itemForBlockModel(ModStoneBlocks.POLISHED_VERDONE_SLAB.get());
        wallWithItem((WallBlock) ModStoneBlocks.POLISHED_VERDONE_WALL.get(), Lithereal.id("block/polished_verdone"));

        blockWithItem(ModOreBlocks.LITHERITE_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_LITHERITE_ORE);
        blockWithItem(ModOreBlocks.ETHERSTONE_LITHERITE_ORE);
        blockWithItem(ModOreBlocks.NERITH_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_NERITH_ORE);
        blockWithItem(ModOreBlocks.ETHERSTONE_NERITH_ORE);
        blockWithItem(ModOreBlocks.LUMINIUM_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_LUMINIUM_ORE);
        blockWithItem(ModOreBlocks.ETHERSTONE_LUMINIUM_ORE);
        blockWithItem(ModOreBlocks.CYRUM_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_CYRUM_ORE);
        blockWithItem(ModOreBlocks.ETHERSTONE_CYRUM_ORE);
        blockWithItem(ModOreBlocks.COPALITE_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_COPALITE_ORE);
        blockWithItem(ModOreBlocks.ETHERSTONE_COPALITE_ORE);
        blockWithItem(ModOreBlocks.AURELITE_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_AURELITE_ORE);
        blockWithItem(ModOreBlocks.ETHERSTONE_AURELITE_ORE);
        blockWithItem(ModOreBlocks.SATURNITE_ORE);
        blockWithItem(ModOreBlocks.HELLIONITE_ORE);
        blockWithItem(ModOreBlocks.ELUNITE_ORE);
        blockWithItem(ModOreBlocks.CHRYON_ORE);
        blockWithItem(ModOreBlocks.ALLIAN_ORE);

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

    private void cubeColumn(Block block, ResourceLocation side, ResourceLocation end) {
        simpleBlock(block, models().cubeColumn(name(block), side, end));
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